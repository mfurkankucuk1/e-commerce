package com.mfk.roomexample.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mfk.roomexample.data.model.Cart
import com.mfk.roomexample.data.model.Product
import com.mfk.roomexample.data.repository.CartRepository
import com.mfk.roomexample.data.repository.PreferencesRepository
import com.mfk.roomexample.utils.Constants
import com.mfk.roomexample.utils.Constants.USER_UUID
import com.mfk.roomexample.utils.CurrentTimeHelper.getCurrentTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by M.Furkan KÜÇÜK on 19.12.2022
 **/
@HiltViewModel
class CartViewModel @Inject constructor(
    application: Application,
    private val preferencesRepository: PreferencesRepository,
    private val cartRepository: CartRepository
) : AndroidViewModel(application) {

    private var _addCartResponse: MutableLiveData<Long?> = MutableLiveData()
    val addCartResponse: LiveData<Long?> get() = _addCartResponse

    private var _getCartInformationResponse: MutableLiveData<Boolean?> = MutableLiveData()
    val getCartInformationResponse: LiveData<Boolean?> get() = _getCartInformationResponse

    private var _getCartItemResponse: MutableLiveData<Cart?> = MutableLiveData()
    val getCartItemResponse: LiveData<Cart?> get() = _getCartItemResponse

    private var _getCustomerCartResponse: MutableLiveData<List<Product>?> = MutableLiveData()
    val getCustomerCartResponse: LiveData<List<Product>?> get() = _getCustomerCartResponse

    fun clearCustomerCartResponse() {
        _getCustomerCartResponse.value = null
    }

    fun getCartItem(productId: Int) = viewModelScope.launch {
        getCartItemSafeCall(productId)
    }

    private suspend fun getCartItemSafeCall(productId: Int) {
        _getCartItemResponse.value = cartRepository.getCartItem(
            preferencesRepository.getStringPreferences(
                USER_UUID
            ), productId
        )
    }


    fun getCartInformation(productId: Int) = viewModelScope.launch {
        getCartInformationSafeCall(productId)
    }

    private suspend fun getCartInformationSafeCall(productId: Int) {
        _getCartInformationResponse.value = cartRepository.getCartInformation(
            preferencesRepository.getStringPreferences(USER_UUID),
            productId
        )
    }

    fun addCart(cart: Cart) = viewModelScope.launch {
        cart.createdAt = getCurrentTime()
        cart.userUUID = preferencesRepository.getStringPreferences(Constants.USER_UUID)
        addCartSafeCall(cart)
    }

    private suspend fun addCartSafeCall(cart: Cart) {
        _addCartResponse.value = cartRepository.addCart(cart)
    }

    fun updateQuantity(productId: Int, quantity: Int) = viewModelScope.launch {
        updateQuantitySafeCall(productId, quantity)
    }

    private suspend fun updateQuantitySafeCall(productId: Int, quantity: Int) {
        cartRepository.updateQuantity(
            preferencesRepository.getStringPreferences(USER_UUID),
            productId,
            quantity
        )
    }

    fun getCustomerCart(userUUID: String) = viewModelScope.launch {
        getCustomerCartSafeCall(userUUID)
    }

    private suspend fun getCustomerCartSafeCall(userUUID: String) {
        _getCustomerCartResponse.value = cartRepository.getCustomerCart(userUUID)
    }

}