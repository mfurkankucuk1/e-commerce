package com.mfk.roomexample.ui.productDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.mfk.roomexample.MainActivity
import com.mfk.roomexample.R
import com.mfk.roomexample.data.model.Favorite
import com.mfk.roomexample.data.model.Product
import com.mfk.roomexample.data.repository.PreferencesRepository
import com.mfk.roomexample.databinding.FragmentProductDetailBinding
import com.mfk.roomexample.ui.adapter.ProductsAdapter
import com.mfk.roomexample.utils.Constants.USER_UUID
import com.mfk.roomexample.utils.CurrencyHelper.getCurrency
import com.mfk.roomexample.utils.CurrentTimeHelper.getCurrentTime
import com.mfk.roomexample.viewModel.FavoriteViewModel
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
    private val favoriteViewModel: FavoriteViewModel by activityViewModels()
    private var productId = -1
    private var categoryName = ""
    private val productsAdapter: ProductsAdapter by lazy { ProductsAdapter() }
    private var productList = ArrayList<Product>()
    private var isFavorite = false

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
        }
        if (categoryName != null) {
            this.categoryName = categoryName as String
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        setupAdapter()
        handleClickEvents()
        subscribeObserve()
    }

    private fun handleClickEvents() {
        binding.apply {
            mvBack.setOnClickListener {
                findNavController().popBackStack()
            }
            mvFavorite.setOnClickListener {
                val favorite = Favorite(
                    userUUID = preferencesRepository.getStringPreferences(USER_UUID),
                    productId = productId,
                    createdAt = getCurrentTime()
                )
                if (isFavorite) {
                    favoriteViewModel.deleteFavorite(
                        preferencesRepository.getStringPreferences(
                            USER_UUID
                        ), productId
                    )
                } else {
                    favoriteViewModel.addFavorite(favorite)

                }
            }
        }
        productsAdapter.apply {
            setOnItemClickListener { product ->
                product.id?.let {
                    productId = it
                }
                product.category?.let {
                    categoryName = it
                }
                getProductDetail()
            }
        }
    }

    private fun setupAdapter() {
        binding.apply {
            rvProducts.adapter = productsAdapter
        }
    }

    private fun subscribeObserve() {
        productViewModel.getSingleProductResponse.observe(viewLifecycleOwner) { response ->
            response?.let { result ->
                populateUI(result)
                productViewModel.clearSingleProductResponse()
            }
        }

        productViewModel.getCategoryProductsResponse.observe(viewLifecycleOwner) { response ->
            response?.let { result ->
                handleProductsResponse(result)
                productViewModel.clearCategoryProductResponse()
            }
        }

        favoriteViewModel.addFavoriteResponse.observe(viewLifecycleOwner) { response ->
            response?.let { result ->
                if (result > 0) {
                    getProductFavorite()
                }
            }
        }

        favoriteViewModel.deleteFavoriteResponse.observe(viewLifecycleOwner) { response ->
            response?.let { result ->
                getProductFavorite()
            }
        }

        favoriteViewModel.getProductFavorite.observe(viewLifecycleOwner) { response ->
            response?.let { result ->
                handleProductFavoriteResponse(result)
            }
        }

    }

    private fun handleProductFavoriteResponse(result: Boolean) {
        binding.apply {
            if (result) {
                isFavorite = true
                imgFavorite.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_check_favorite
                    )
                )
            } else {
                isFavorite = false
                imgFavorite.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_uncheck_favorite
                    )
                )
            }
        }

    }

    private fun getProductFavorite() {
        favoriteViewModel.getProductFavorite(
            preferencesRepository.getStringPreferences(
                USER_UUID
            ), productId
        )
    }

    private fun handleProductsResponse(result: List<Product>) {
        productList = ArrayList()
        for (i in result) {
            if (productId != i.id) {
                productList.add(i)
            }
        }
        productsAdapter.list = productList
    }

    private fun populateUI(result: Product) {
        binding.apply {
            Glide.with(requireContext())
                .load(result.image)
                .fitCenter()
                .into(imgProduct)
            tvProductName.text = result.title
            result.price?.let { prc ->
                tvProductPrice.text = getCurrency(prc)
            } ?: run {
                btnAddCart.isEnabled = false
            }

        }
    }

    private fun initialize() {
        (requireActivity() as? MainActivity)?.let { activity ->
            activity.hideBottomNavigation()
        }
        getProductDetail()
    }

    private fun getProductDetail() {
        productViewModel.getSingleProduct(productId)
        productViewModel.getCategoryProduct(categoryName)
        getProductFavorite()
    }


}