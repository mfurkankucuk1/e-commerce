package com.mfk.roomexample.ui

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.mfk.roomexample.MainActivity
import com.mfk.roomexample.data.model.Product
import com.mfk.roomexample.data.model.SortEnum
import com.mfk.roomexample.databinding.FragmentProductsBinding
import com.mfk.roomexample.databinding.LayoutDialogFilterBinding
import com.mfk.roomexample.databinding.LayoutDialogSortBinding
import com.mfk.roomexample.ui.adapter.FilterAdapter
import com.mfk.roomexample.ui.adapter.ProductsAdapter
import com.mfk.roomexample.utils.CurrentTimeHelper.getCurrentTime
import com.mfk.roomexample.utils.Resource
import com.mfk.roomexample.viewModel.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class ProductsFragment : Fragment() {

    private var _binding: FragmentProductsBinding? = null
    private val binding: FragmentProductsBinding get() = _binding!!
    private val productViewModel: ProductViewModel by activityViewModels()
    private val productsAdapter: ProductsAdapter by lazy { ProductsAdapter() }
    private val filterAdapter: FilterAdapter by lazy { FilterAdapter() }
    private var productsList = ArrayList<Product>()
    val dialogBinding: LayoutDialogFilterBinding? = null
    private var isSelectFilter = false
    private var productTempList = ArrayList<Product>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        handleClickEvents()
        setupAdapter()
        subscribeObserve()
        setupSearch()
    }

    private fun handleClickEvents() {
        binding.apply {
            btnSort.setOnClickListener {
                shorPopup()
            }
            btnFilter.setOnClickListener {
                filterPopup()
            }
        }
    }

    private fun setupAdapter() {
        binding.apply {
            rvProducts.setHasFixedSize(true)
            rvProducts.adapter = productsAdapter
            rvProducts.layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }

    private fun setupSearch() {
        binding.etSearch.doOnTextChanged { inputText, _, _, _ ->
            if (inputText != null) {
                if (inputText.isEmpty()) {
                    if (productsList.size != 0)
                        productsAdapter.list = productsList
                } else {

                    val currentTextLength = inputText.length
                    if (currentTextLength >= 2) {
                        Handler(Looper.getMainLooper()).postDelayed({
                            val productFilterList =
                                productsList.filter { it.title!!.lowercase().contains(inputText) }
                            val productArray = ArrayList<Product>()
                            productArray.addAll(productFilterList)
                            productsAdapter.list = productArray
                        }, 500)
                    }
                }
            }
        }
    }

    private fun subscribeObserve() {
        productViewModel.getProductResponseInAPI.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Error -> {
                    setupShimmer(false)
                    productViewModel.clearGetProductResponseInAPI()
                }
                is Resource.Loading -> {
                    setupShimmer(true)
                }
                is Resource.Success -> {
                    response.data?.let { result ->
                        productsList = ArrayList()
                        productsList.addAll(result)
                        setupInsertDataToDb(result)
                        handleProductsSuccessResponse(result)
                    }
                    productViewModel.clearGetProductResponseInAPI()
                    setupShimmer(false)
                }
            }
        }
    }

    private fun handleProductsSuccessResponse(result: List<Product>) {
        productsList = ArrayList()
        productsList.addAll(result)
        productsAdapter.list = productsList
    }

    private fun setupShimmer(loading: Boolean) {
        if (loading) {
            binding.rvProducts.showShimmer()
        } else {
            binding.rvProducts.hideShimmer()
        }
    }

    private fun setupInsertDataToDb(result: List<Product>) {

        for (i in result) {
            i.createdAt = getCurrentTime()
            i.isAddedCart = false
            i.isAddedFavorite = false
            productViewModel.insertDataToDb(i)
        }
    }

    private fun initialize() {
        (requireActivity() as? MainActivity)?.let { activity->
            activity.showBottomNavigation()
        }
        if (productViewModel.getProductInDb().isEmpty()) {
            productViewModel.getProductWithAPI()
        }else{
            handleProductsSuccessResponse(productViewModel.getProductInDb())
        }
    }

    private fun shorPopup(): AlertDialog {
        val builder = AlertDialog.Builder(context).create()
        val dialogBinding = LayoutDialogSortBinding.inflate(layoutInflater)
        dialogBinding.apply {
            // Rising Price
            checkAsc.setOnClickListener {
                builder.dismiss()
                if (!isSelectFilter) {
                    setupAdapterList(productViewModel.getSortProductWithPrice(SortEnum.ASC.type))
                } else {
                    setupSortWithFilter(type = SortEnum.ASC.type)
                }

            }
            // Declining in price
            checkDesc.setOnClickListener {
                builder.dismiss()
                if (!isSelectFilter) {
                    setupAdapterList(productViewModel.getSortProductWithPrice(SortEnum.DESC.type))
                } else {
                    setupSortWithFilter(type = SortEnum.DESC.type)
                }

            }

            checkDefault.setOnClickListener {
                builder.dismiss()
                if (!isSelectFilter) {
                    setupAdapterList(productViewModel.getProductInDb())
                } else {
                    setupAdapterList(productTempList)
                }
            }
        }
        builder.setView(dialogBinding.root)
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE)
        builder.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        builder.show()
        return builder
    }

    private fun setupSortWithFilter(type: Int) {
        val productAdapterList = productsAdapter.list
        when (type) {
            SortEnum.ASC.type -> {
                val productAdapterSortedList = productAdapterList.sortedBy { it.price }
                productsList = ArrayList()
                productsList.addAll(productAdapterSortedList)
                productsAdapter.list = productsList
            }
            SortEnum.DESC.type -> {
                val productAdapterSortedList = productAdapterList.sortedByDescending { it.price }
                productsList = ArrayList()
                productsList.addAll(productAdapterSortedList)
                productsAdapter.list = productsList
            }
        }
    }


    private fun filterPopup(): AlertDialog {
        val builder = AlertDialog.Builder(context).create()
        val dialogBinding = LayoutDialogFilterBinding.inflate(layoutInflater)
        dialogBinding.apply {
            rvFilter.apply {
                adapter = filterAdapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

            }
            tvClearFilter.setOnClickListener {
                builder.dismiss()
                isSelectFilter = false
                setupAdapterList(productViewModel.getProductInDb())
            }

            filterAdapter.list = productViewModel.getCountAnDFilter()
            builder.setView(dialogBinding.root)
            builder.requestWindowFeature(Window.FEATURE_NO_TITLE)
            builder.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            builder.show()
            filterAdapter.apply {
                setOnClickItemListener { category ->
                    builder.dismiss()
                    category.category?.let { name ->
                        isSelectFilter = true
                        productTempList = ArrayList()
                        productTempList.addAll(productViewModel.getCategoryFilter(name))
                        setupAdapterList(productViewModel.getCategoryFilter(name))
                    }
                }
            }


        }
        return builder
    }

    private fun setupAdapterList(list: List<Product>) {
        productsList = ArrayList()
        productsList.addAll(list)
        productsAdapter.list = productsList
        binding.rvProducts.scrollToPosition(0)
    }

}