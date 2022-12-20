package com.mfk.roomexample.utils

import com.mfk.roomexample.data.model.CartModel

/**
 * Created by M.Furkan KÜÇÜK on 20.12.2022
 **/
object TotalPriceHelper {
    fun totalPriceCalculate(list: List<CartModel>): Double {
        var totalPrice = 0.0
        for (i in list) {
            totalPrice += (i.quantity * i.price)
        }
        return totalPrice
    }
}