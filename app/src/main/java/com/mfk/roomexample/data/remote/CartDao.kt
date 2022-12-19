package com.mfk.roomexample.data.remote

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mfk.roomexample.data.model.Cart

/**
 * Created by M.Furkan KÜÇÜK on 19.12.2022
 **/
@Dao
interface CartDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCart(cart: Cart): Long

    @Query("SELECT CASE WHEN EXISTS (SELECT * FROM Cart WHERE userUUID=:userUUID AND productId=:productId) THEN 1 ELSE 0 END")
    suspend fun getCartInformation(userUUID: String, productId: Int): Boolean

    @Query("SELECT * FROM Cart WHERE userUUID=:userUUID AND productId=:productId ")
    suspend fun getCartItem(userUUID: String, productId: Int): Cart
//    @Query("UPDATE Products SET isAddedFavorite =:isAdded WHERE id=:id")

    @Query("UPDATE Cart SET quantity =:quantity WHERE (userUUID=:userUUID AND productId=:productId)")
    suspend fun updateQuantity(userUUID: String, productId: Int, quantity: Int)
}