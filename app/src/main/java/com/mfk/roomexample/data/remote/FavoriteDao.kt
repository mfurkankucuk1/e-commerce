package com.mfk.roomexample.data.remote

import androidx.room.*
import com.mfk.roomexample.data.model.Favorite
import com.mfk.roomexample.data.model.Product

/**
 * Created by M.Furkan KÜÇÜK on 19.12.2022
 **/
@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favorite: Favorite): Long

    @Query("SELECT CASE WHEN EXISTS (SELECT * FROM Favorites WHERE userUUID=:userUUD AND productId=:productId) THEN 1 ELSE 0 END")
    fun getProductFavorite(userUUD: String, productId: Int): Boolean


    @Query("DELETE FROM Favorites WHERE (userUUID=:userUUID AND productId=:productId)")
    suspend fun deleteFavorite(userUUID: String, productId: Int):Int

}