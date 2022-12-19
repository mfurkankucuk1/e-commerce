package com.mfk.roomexample.data.repository

import com.mfk.roomexample.data.model.Cart
import com.mfk.roomexample.data.remote.CartDao
import javax.inject.Inject

/**
 * Created by M.Furkan KÜÇÜK on 19.12.2022
 **/
class CartRepository @Inject constructor(private val cartDao: CartDao) {

    suspend fun addCart(cart: Cart) = cartDao.addCart(cart)

    suspend fun getCartInformation(userUUID: String, productId: Int) =
        cartDao.getCartInformation(userUUID, productId)

    suspend fun getCartItem(userUUID: String, productId: Int) =
        cartDao.getCartItem(userUUID, productId)

    suspend fun updateQuantity(userUUID: String, productId: Int, quantity: Int) =
        cartDao.updateQuantity(userUUID, productId, quantity)

}