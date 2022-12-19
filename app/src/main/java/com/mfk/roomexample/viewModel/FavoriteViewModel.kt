package com.mfk.roomexample.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mfk.roomexample.data.model.Favorite
import com.mfk.roomexample.data.repository.FavoriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by M.Furkan KÜÇÜK on 19.12.2022
 **/
@HiltViewModel
class FavoriteViewModel @Inject constructor(
    application: Application,
    private val favoriteRepository: FavoriteRepository
) : AndroidViewModel(application) {

    private var _addFavoriteResponse: MutableLiveData<Long?> = MutableLiveData()
    val addFavoriteResponse: LiveData<Long?> get() = _addFavoriteResponse

    private var _deleteFavoriteResponse: MutableLiveData<Int?> = MutableLiveData()
    val deleteFavoriteResponse: LiveData<Int?> get() = _deleteFavoriteResponse

    private var _getProductFavoriteResponse: MutableLiveData<Boolean?> = MutableLiveData()
    val getProductFavorite: LiveData<Boolean?> get() = _getProductFavoriteResponse

    fun getProductFavorite(userUUD: String, productId: Int){
        getProductFavoriteSafeCall(userUUD,productId)
    }

    private fun getProductFavoriteSafeCall(userUUD: String, productId: Int) {
        _getProductFavoriteResponse.value = favoriteRepository.getProductFavorite(userUUD,productId)
    }

    fun addFavorite(favorite: Favorite) = viewModelScope.launch {
        addFavoriteSafeCall(favorite)
    }

    private suspend fun addFavoriteSafeCall(favorite: Favorite) {
        _addFavoriteResponse.value = favoriteRepository.addFavorite(favorite)
    }

    fun deleteFavorite(userUUD: String, productId: Int) = viewModelScope.launch {
        deleteFavoriteSafeCall(userUUD, productId)
    }

    private suspend fun deleteFavoriteSafeCall(userUUD: String, productId: Int) {
        _deleteFavoriteResponse.value = favoriteRepository.deleteFavorite(userUUD, productId)
    }

}