package com.mfk.roomexample.data.remote

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mfk.roomexample.data.model.Category
import com.mfk.roomexample.data.model.Product
import com.mfk.roomexample.utils.Resource

/**
 * Created by M.Furkan KÜÇÜK on 7.12.2022
 **/
@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(product: Product): Long

    @Query("SELECT * FROM Products")
    fun getProducts(): List<Product>

    @Query("SELECT COUNT(id) as count, category FROM Products GROUP BY category")
    fun getCountAnFilter(): List<Product>

    @Query("SELECT * FROM Products WHERE category = :category")
    fun getCategoryFilter(category: String): List<Product>

    @Query("SELECT * FROM Products ORDER BY CASE WHEN :isAsc = 1 THEN price END ASC,CASE WHEN :isAsc = 0 THEN price END DESC")
    fun getProductSortWithPrice(isAsc: Int): List<Product>

    @Query("SELECT * FROM PRODUCTS WHERE isAddedFavorite = 0 ")
    fun getUnFavoriteProduct(): List<Product>

    @Query("UPDATE Products SET isAddedFavorite =:isAdded WHERE id=:id")
    fun updateAddFavorite(id: Int, isAdded: Int)

    @Delete
    suspend fun delete(product: Product)
}