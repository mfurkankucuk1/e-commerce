package com.mfk.roomexample.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mfk.roomexample.data.model.User
import com.mfk.roomexample.data.repository.UserRepository
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

    private var _getUserResponse: MutableLiveData<User?> = MutableLiveData()
    val getUserResponse: LiveData<User?> get() = _getUserResponse

    private var _getUserForLoginResponse: MutableLiveData<User?> = MutableLiveData()
    val getUserForLoginResponse: LiveData<User?> get() = _getUserForLoginResponse

    fun clearUserResponse() {
        _getUserResponse.value = null
    }

    fun clearUserForLoginResponse() {
        _getUserForLoginResponse.value = null
    }

    fun clearUserLoginResponse() {
        _userLoginResponse.value = null
    }

    fun createUser(user: User) = viewModelScope.launch {
        createUserSafeCall(user)
    }

    private suspend fun createUserSafeCall(user: User) {
        userRepository.createUser(user)
    }

    fun getUser(id:String){
        getUserSafeCall(id)
    }

    fun getUserForLogin(userNameOrEmail: String, password: String){
        getUserForLoginSafeCall(userNameOrEmail,password)
    }

    private fun getUserForLoginSafeCall(userNameOrEmail: String, password: String) {
        _getUserForLoginResponse.value = userRepository.getUserForLogin(userNameOrEmail, password)
    }

    private fun getUserSafeCall(id: String) {
        _getUserResponse.value = userRepository.getUser(id)
    }

    fun loginUser(userNameOrEmail: String, password: String) {
        loginUserSafeCall(userNameOrEmail, password)
    }

    private fun loginUserSafeCall(userNameOrEmail: String, password: String) {
        _userLoginResponse.value = userRepository.loginUser(userNameOrEmail, password)
    }

}