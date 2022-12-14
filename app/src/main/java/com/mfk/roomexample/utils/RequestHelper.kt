package com.mfk.roomexample.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.mfk.roomexample.utils.Constants.ERROR
import com.mfk.roomexample.utils.Constants.ERROR_401
import com.mfk.roomexample.utils.Constants.ERROR_500
import retrofit2.Response

/**
 * Created by M.Furkan KÜÇÜK on 8.12.2022
 **/
object RequestHelper {

    fun <T> handleResponse(response: Response<T>): Resource<T> {
        if (response != null) {
            if (response.isSuccessful) {
                response.body()?.let { result ->
                    return Resource.Success(result) as Resource<T>
                }
            } else {
                val responseCode = response.code()

                if (responseCode == 401) {
                    return Resource.Error(ERROR_401)
                } else if (responseCode in 500..599) {
                    return Resource.Error(ERROR_500)
                }
            }
        }
        return Resource.Error(ERROR)
    }
    

}