package com.mfk.roomexample.data.remote

import com.mfk.roomexample.data.model.Product
import com.mfk.roomexample.utils.Constants
import retrofit2.Response
import retrofit2.http.GET

/**
 * Created by M.Furkan KÜÇÜK on 7.12.2022
 **/
interface ProductService {

    @GET(Constants.PRODUCTS_END_POINTS)
    suspend fun getProducts():Response<List<Product>>

}