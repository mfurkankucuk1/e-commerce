package com.mfk.roomexample.utils

import java.util.*

/**
 * Created by M.Furkan KÜÇÜK on 14.12.2022
 **/
object CurrentTimeHelper {

    fun getCurrentTime(): String {
        val currentTime: Date = Calendar.getInstance().time
        return currentTime.toString()
    }

}