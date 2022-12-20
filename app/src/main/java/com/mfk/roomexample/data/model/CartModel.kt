package com.mfk.roomexample.data.model

/**
 * Created by M.Furkan KÜÇÜK on 20.12.2022
 **/
data class CartModel(
    var id: Int,
    var title: String,
    var price: Double,
    var image: String,
    var quantity: Int,
    var description: String
)
