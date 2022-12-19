package com.mfk.roomexample.ui.productDetail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.mfk.roomexample.MainActivity
import com.mfk.roomexample.R
import com.mfk.roomexample.data.repository.PreferencesRepository
import com.mfk.roomexample.databinding.FragmentProductDetailBinding
import com.mfk.roomexample.utils.CurrencyHelper.getCurrency
import com.mfk.roomexample.viewModel.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProductDetailFragment : Fragment() {

    @Inject
    lateinit var preferencesRepository: PreferencesRepository
    private var _binding: FragmentProductDetailBinding? = null
    private val binding: FragmentProductDetailBinding get() = _binding!!
    private val productViewModel: ProductViewModel by activityViewModels()
    private var productId = -1
    private var categoryName = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleArgument()
    }

    private fun handleArgument() {
        val productId = arguments?.get(getString(R.string.product_id_bundle_key))
        val categoryName =
            arguments?.get(resources.getString(R.string.product_category_name_bundle_key))
        if (productId != null) {
            this.productId = productId as Int
            Log.e(this@ProductDetailFragment::class.java.name, "handleArgument: PRODUCTID:${productId} ")
        }
        if (categoryName != null) {
            this.categoryName = categoryName as String
            Log.e(this@ProductDetailFragment::class.java.name, "handleArgument:CATEGORYNAME${categoryName} ")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        subscribeObserve()
    }

    private fun subscribeObserve() {
        productViewModel.getSingleProductResponse.observe(viewLifecycleOwner) { response ->
            response?.let { result ->
                Log.e("TAG", "subscribeObserve: ${getCurrency(result.price!!)} ")
            }
            productViewModel.clearSingleProductResponse()
        }

        productViewModel.getCategoryProductsResponse.observe(viewLifecycleOwner) { response ->
            response?.let { result ->
                Log.e(
                    this@ProductDetailFragment::class.java.name,
                    "subscribeObserve: ${result.get(0).price}",
                )
            }
        }
        productViewModel.clearCategoryProductResponse()
    }

    private fun initialize() {
        (requireActivity() as? MainActivity)?.let { activity ->
            activity.hideBottomNavigation()
        }
        productViewModel.getSingleProduct(productId)
        productViewModel.getCategoryProduct(categoryName)
    }


}