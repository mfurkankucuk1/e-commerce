package com.mfk.roomexample.data.remote

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mfk.roomexample.data.model.*
import com.mfk.roomexample.utils.Resource

/**
 * Created by M.Furkan KÜÇÜK on 7.12.2022
 **/
@Dao
interface DatabaseDao {


    /**
     * PRODUCT
     * **/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(product: Product): Long

    @Query("SELECT * FROM Products")
    fun getProducts(): List<Product>

    @Query("SELECT COUNT(id) as count, category FROM Products GROUP BY category")
    fun getCountAnFilter(): List<Product>

    @Query("SELECT * FROM Products WHERE category = :category")
    fun getCategoryFilter(category: String): List<Product>

    @Query("SELECT * FROM Products WHERE category LIKE :category")
    fun getProductsWithCategory(category: String): List<Product>

    @Query("SELECT * FROM Products ORDER BY CASE WHEN :isAsc = 1 THEN price END ASC,CASE WHEN :isAsc = 0 THEN price END DESC")
    fun getProductSortWithPrice(isAsc: Int): List<Product>

    @Query("SELECT * FROM PRODUCTS WHERE isAddedFavorite = 0 ")
    fun getUnFavoriteProduct(): List<Product>

    @Query("UPDATE Products SET isAddedFavorite =:isAdded WHERE id=:id")
    fun updateAddFavorite(id: Int, isAdded: Int)

    @Query("SELECT * FROM Products WHERE id=:productId")
    fun getSingleProduct(productId: Int): Product

    @Delete
    suspend fun delete(product: Product)

    /**
     * FAVORITE
     * **/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favorite: Favorite): Long


    @Query("SELECT CASE WHEN EXISTS (SELECT * FROM Favorites WHERE userUUID=:userUUD AND productId=:productId) THEN 1 ELSE 0 END")
    fun getProductFavorite(userUUD: String, productId: Int): Boolean


    @Query("DELETE FROM Favorites WHERE (userUUID=:userUUID AND productId=:productId)")
    suspend fun deleteFavorite(userUUID: String, productId: Int): Int

    @Query("SELECT * FROM Products INNER JOIN Favorites ON Products.id = Favorites.productId WHERE userUUID=:userUUID")
    suspend fun getCustomerFavorites(userUUID: String): List<FavoriteModel>


    /**
     * USER
     * **/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User): Long

    @Query("SELECT * FROM User WHERE id=:id")
    fun getUser(id:String): User

    @Query("SELECT * FROM User WHERE(userName=:userNameOrEmail OR email=:userNameOrEmail) AND password=:password")
    fun getUserForLogin(userNameOrEmail: String, password: String): User

    @Query("SELECT CASE WHEN EXISTS (SELECT * FROM User WHERE (userName=:userNameOrEmail OR email=:userNameOrEmail) AND password=:password ) THEN 1 ELSE 0 END")
    fun loginUser(userNameOrEmail: String, password: String):Boolean

/**
 * CART
 * **/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCart(cart: Cart): Long

    @Query("SELECT CASE WHEN EXISTS (SELECT * FROM Cart WHERE userUUID=:userUUID AND productId=:productId) THEN 1 ELSE 0 END")
    suspend fun getCartInformation(userUUID: String, productId: Int): Boolean

    @Query("SELECT * FROM Cart WHERE userUUID=:userUUID AND productId=:productId ")
    suspend fun getCartItem(userUUID: String, productId: Int): Cart

    @Query("UPDATE Cart SET quantity =:quantity WHERE (userUUID=:userUUID AND productId=:productId)")
    suspend fun updateQuantity(userUUID: String, productId: Int, quantity: Int)

    @Transaction
    @Query("SELECT * FROM Products INNER JOIN Cart ON Products.id = Cart.productId WHERE userUUID=:userUUID")
    suspend fun getCustomerCart(userUUID: String): List<CartModel>




}