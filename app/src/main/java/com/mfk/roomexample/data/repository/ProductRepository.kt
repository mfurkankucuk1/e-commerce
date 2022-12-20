package com.mfk.roomexample.data.repository

import com.mfk.roomexample.data.model.Product
import com.mfk.roomexample.data.remote.DatabaseDao
import com.mfk.roomexample.data.remote.ProductService
import javax.inject.Inject

/**
 * Created by M.Furkan KÜÇÜK on 7.12.2022
 **/
class ProductRepository @Inject constructor(
    private val databaseDao: DatabaseDao,
    private val productService: ProductService
) {

    /**
     * Get Products with API
     * **/
    suspend fun getProductWithAPI() = productService.getProducts()

    /**
     * Get Products with database
     * **/

    fun getProductsWithDb() = databaseDao.getProducts()

    fun getUnFavoriteProduct() = databaseDao.getUnFavoriteProduct()

    fun getCountAnFilter() = databaseDao.getCountAnFilter()

    fun getProductSortWithPrice(type:Int) = databaseDao.getProductSortWithPrice(type)

    fun getCategoryFilter(category:String) = databaseDao.getCategoryFilter(category)

    fun getProductsWithCategory(category:String) = databaseDao.getProductsWithCategory(category)

    fun updateAddFavorite(id: Int, isAdded: Int) = databaseDao.updateAddFavorite(id,isAdded)

    fun getSingleProduct(productId:Int) = databaseDao.getSingleProduct(productId)

    /**
     * Inset Products to database
     * **/

    suspend fun insertProducts(products: Product) = databaseDao.insert(products)

}