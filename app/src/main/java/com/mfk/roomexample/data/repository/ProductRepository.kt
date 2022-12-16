package com.mfk.roomexample.data.repository

import com.mfk.roomexample.data.model.Product
import com.mfk.roomexample.data.remote.ProductDao
import com.mfk.roomexample.data.remote.ProductService
import javax.inject.Inject

/**
 * Created by M.Furkan KÜÇÜK on 7.12.2022
 **/
class ProductRepository @Inject constructor(
    private val productDao: ProductDao,
    private val productService: ProductService
) {

    /**
     * Get Products with API
     * **/
    suspend fun getProductWithAPI() = productService.getProducts()

    /**
     * Get Products with database
     * **/

    fun getProductsWithDb() = productDao.getProducts()

    fun getUnFavoriteProduct() = productDao.getUnFavoriteProduct()

    fun getCountAnFilter() = productDao.getCountAnFilter()

    fun getProductSortWithPrice(type:Int) = productDao.getProductSortWithPrice(type)

    fun getCategoryFilter(category:String) = productDao.getCategoryFilter(category)

    fun updateAddFavorite(id: Int, isAdded: Int) = productDao.updateAddFavorite(id,isAdded)

    fun getSingleProduct(productId:Int) = productDao.getSingleProduct(productId)

    /**
     * Inset Products to database
     * **/

    suspend fun insertProducts(products: Product) = productDao.insert(products)

}