package com.mfk.roomexample.data.repository

import com.mfk.roomexample.data.model.Favorite
import com.mfk.roomexample.data.remote.FavoriteDao
import javax.inject.Inject

/**
 * Created by M.Furkan KÜÇÜK on 19.12.2022
 **/
class FavoriteRepository @Inject constructor(private val favoriteDao: FavoriteDao) {

    suspend fun addFavorite(favorite: Favorite) = favoriteDao.insert(favorite)

    fun getProductFavorite(userUUD: String, productId: Int) = favoriteDao.getProductFavorite(userUUD,productId)

    suspend fun deleteFavorite(userUUD: String, productId: Int) = favoriteDao.deleteFavorite(userUUD, productId)

}