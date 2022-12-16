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
        if (productId != null) {
            this.productId = productId as Int
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
        }
    }

    private fun initialize() {
        (requireActivity() as? MainActivity)?.let { activity ->
            activity.hideBottomNavigation()
        }
        productViewModel.getSingleProduct(productId)
    }


}