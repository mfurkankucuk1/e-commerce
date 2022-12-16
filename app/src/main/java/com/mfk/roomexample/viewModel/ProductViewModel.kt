package com.mfk.roomexample.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.networkhelperlibrary.NetworkHelper
import com.mfk.roomexample.data.model.Category
import com.mfk.roomexample.data.model.Product
import com.mfk.roomexample.data.repository.ProductRepository
import com.mfk.roomexample.utils.Constants.NOT_FOUND
import com.mfk.roomexample.utils.Constants.NO_INTERNET_CONNECTION
import com.mfk.roomexample.utils.RequestHelper
import com.mfk.roomexample.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by M.Furkan KÜÇÜK on 7.12.2022
 **/
@HiltViewModel
class ProductViewModel @Inject constructor(
    application: Application,
    private val productRepository: ProductRepository
) : AndroidViewModel(application) {

    private var _getProductResponseInAPI: MutableLiveData<Resource<List<Product>>?> =
        MutableLiveData()
    val getProductResponseInAPI: MutableLiveData<Resource<List<Product>>?> get() = _getProductResponseInAPI

    private var _getSingleProductResponse: MutableLiveData<Product?> = MutableLiveData()
    val getSingleProductResponse: LiveData<Product?> get() = _getSingleProductResponse

    fun clearSingleProductResponse() {
        _getSingleProductResponse.value = null
    }

    fun clearGetProductResponseInAPI() {
        _getProductResponseInAPI.value = null
    }

    fun getCountAnDFilter(): ArrayList<Category> {
        val list = ArrayList<Category>()
        for (i in productRepository.getCountAnFilter()) {
            list.add(Category(i.count, i.category))
        }
        return list
    }

    fun getSortProductWithPrice(type: Int): List<Product> {
        return productRepository.getProductSortWithPrice(type)
    }

    fun getProductInDb(): List<Product> {
        return productRepository.getProductsWithDb()
    }

    fun getUnFavoriteProduct(): List<Product> {
        return productRepository.getUnFavoriteProduct()
    }

    fun getCategoryFilter(category: String): List<Product> {
        return productRepository.getCategoryFilter(category)
    }

    fun updateAddFavorite(id: Int, isAdded: Int) {
        productRepository.updateAddFavorite(id, isAdded)
    }


    fun insertDataToDb(product: Product) = viewModelScope.launch {
        insertDataToDbSafeCall(product)
    }

    private suspend fun insertDataToDbSafeCall(product: Product) {
        productRepository.insertProducts(product)
    }

    fun getProductWithAPI() = viewModelScope.launch {
        getProductWithAPISafeCall()
    }

    private suspend fun getProductWithAPISafeCall() {
        _getProductResponseInAPI.value = Resource.Loading()
        if (NetworkHelper().isNetworkAvailable(getApplication())) {
            try {
                val response = productRepository.getProductWithAPI()
                _getProductResponseInAPI.value = RequestHelper.handleResponse(response)
            } catch (e: Exception) {
                _getProductResponseInAPI.postValue(Resource.Error(NOT_FOUND))
                e.printStackTrace()
            }
        } else {
            _getProductResponseInAPI.postValue(Resource.Error(NO_INTERNET_CONNECTION))
        }
    }

    fun getSingleProduct(productId: Int) {
        getSingleProductSafeCall(productId)
    }

    private fun getSingleProductSafeCall(productId: Int) {
        _getSingleProductResponse.value = productRepository.getSingleProduct(productId)
    }

}