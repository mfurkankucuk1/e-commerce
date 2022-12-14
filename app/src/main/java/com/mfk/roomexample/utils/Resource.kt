package com.mfk.roomexample.utils

/**
 * Created by M.Furkan KÜÇÜK on 8.12.2022
 **/
sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T : Any>(data: T?) : Resource<T>(data)
    class Error<T>(message: String?, data: T? = null):Resource<T>(data,message)
    class Loading<T>:Resource<T>()
}