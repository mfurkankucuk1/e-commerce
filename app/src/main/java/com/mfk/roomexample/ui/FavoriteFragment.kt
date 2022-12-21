package com.mfk.roomexample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.mfk.roomexample.R
import com.mfk.roomexample.data.model.Product
import com.mfk.roomexample.data.repository.PreferencesRepository
import com.mfk.roomexample.databinding.FragmentFavoriteBinding
import com.mfk.roomexample.ui.adapter.ProductsAdapter
import com.mfk.roomexample.utils.Constants.USER_UUID
import com.mfk.roomexample.viewModel.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    @Inject
    lateinit var preferencesRepository: PreferencesRepository
    private var _binding: FragmentFavoriteBinding? = null
    private val binding: FragmentFavoriteBinding get() = _binding!!
    private val favoriteViewModel: FavoriteViewModel by activityViewModels()
    private val productsAdapter: ProductsAdapter by lazy { ProductsAdapter() }
    private var favoritesList = ArrayList<Product>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        handleClickEvents()
        setupAdapter()
        subscribeObserve()
    }

    private fun subscribeObserve() {
        favoriteViewModel.getCustomerFavoriteResponse.observe(viewLifecycleOwner) { response ->
            response?.let { result ->
                handleFavoritesResponse(result)
                favoriteViewModel.clearCustomerFavorites()
            }
        }
    }

    private fun handleFavoritesResponse(result: List<Product>) {
        favoritesList = ArrayList()
        favoritesList.addAll(result)
        productsAdapter.list = favoritesList
    }

    private fun setupAdapter() {
        binding.apply {
            binding.apply {
                rvFavorites.setHasFixedSize(true)
                rvFavorites.adapter = productsAdapter
                rvFavorites.layoutManager = GridLayoutManager(requireContext(), 2)
            }
        }
    }

    private fun handleClickEvents() {
        binding.apply {
            imgBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
        productsAdapter.apply {
            setOnItemClickListener { product ->
                val bundle = Bundle().apply {
                    product.id?.let { productId ->
                        putInt(resources.getString(R.string.product_id_bundle_key), productId)
                    }
                    product.category?.let { categoryName ->
                        putString(
                            resources.getString(R.string.product_category_name_bundle_key),
                            categoryName
                        )
                    }
                }
                findNavController().navigate(R.id.favoriteFragment_to_productDetailFragment, bundle)
            }
        }
    }

    private fun initialize() {
        favoriteViewModel.getCustomerFavorites(preferencesRepository.getStringPreferences(USER_UUID))
    }
}