package com.mfk.roomexample.utils

import timber.log.Timber
import java.text.NumberFormat
import java.util.*

/**
 * Created by M.Furkan KÜÇÜK on 9.12.2022
 **/
object CurrencyHelper {

    fun getCurrency(price: Double): String {
        val format: NumberFormat = NumberFormat.getCurrencyInstance(Locale.getDefault())
        return format.format(price)

    }
}