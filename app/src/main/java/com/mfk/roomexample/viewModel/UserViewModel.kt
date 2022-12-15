package com.mfk.roomexample.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mfk.roomexample.data.model.User
import com.mfk.roomexample.data.repository.UserRepository
import com.mfk.roomexample.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by M.Furkan KÜÇÜK on 14.12.2022
 **/
@HiltViewModel
class UserViewModel @Inject constructor(
    application: Application,
    private val userRepository: UserRepository
) : AndroidViewModel(application) {

    private var _userLoginResponse: MutableLiveData<Boolean?> = MutableLiveData()
    val userLoginResponse: LiveData<Boolean?> get() = _userLoginResponse

    fun clearUserLoginResponse(){
        _userLoginResponse.value = null
    }

    fun createUser(user: User) = viewModelScope.launch {
        createUserSafeCall(user)
    }

    private suspend fun createUserSafeCall(user: User) {
        userRepository.createUser(user)
    }

    fun loginUser(userNameOrEmail: String, password: String) {
         loginUserSafeCall(userNameOrEmail, password)
    }

    private fun loginUserSafeCall(userNameOrEmail: String, password: String) {
        _userLoginResponse.value = userRepository.loginUser(userNameOrEmail, password)
    }

}