package com.mfk.roomexample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.mfk.roomexample.R
import com.mfk.roomexample.data.model.CartModel
import com.mfk.roomexample.data.repository.PreferencesRepository
import com.mfk.roomexample.databinding.FragmentCartBinding
import com.mfk.roomexample.ui.adapter.CartAdapter
import com.mfk.roomexample.utils.Constants.USER_UUID
import com.mfk.roomexample.utils.CurrencyHelper.getCurrency
import com.mfk.roomexample.utils.TotalPriceHelper.totalPriceCalculate
import com.mfk.roomexample.viewModel.CartViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CartFragment : Fragment() {

    @Inject
    lateinit var preferencesRepository: PreferencesRepository
    private var _binding: FragmentCartBinding? = null
    private val binding: FragmentCartBinding get() = _binding!!
    private val cartViewModel: CartViewModel by activityViewModels()
    private val cartAdapter: CartAdapter by lazy { CartAdapter() }
    private var cartList = ArrayList<CartModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        setupAdapter()
        subscribeObserve()
    }

    private fun setupAdapter() {
        binding.rvCart.apply {
            adapter = cartAdapter
        }
    }

    private fun subscribeObserve() {
        cartViewModel.getCustomerCartResponse.observe(viewLifecycleOwner) { response ->
            response?.let { result ->
                populateTotalPrice(result)
                handleCartResponse(result)
                cartViewModel.clearCustomerCartResponse()
            }
        }
    }

    private fun populateTotalPrice(result: List<CartModel>) {
        binding.tvBasketPrice.text = "${resources.getString(R.string.basket_total_price)} : ${
            getCurrency(
                totalPriceCalculate(result)
            )
        }"
    }

    private fun handleCartResponse(result: List<CartModel>) {
        cartList = ArrayList()
        cartList.addAll(result)
        cartAdapter.list = cartList
    }

    private fun initialize() {
        cartViewModel.getCustomerCart(preferencesRepository.getStringPreferences(USER_UUID))
    }

}