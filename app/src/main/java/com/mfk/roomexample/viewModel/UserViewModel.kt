package com.mfk.roomexample.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
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

    fun createUser(user: User) = viewModelScope.launch {
        createUserSafeCall(user)
    }

    private suspend fun createUserSafeCall(user: User) {
        userRepository.createUser(user)
    }

}