package com.mfk.roomexample.data.repository

import com.mfk.roomexample.data.model.User
import com.mfk.roomexample.data.remote.UserDao
import javax.inject.Inject

/**
 * Created by M.Furkan KÜÇÜK on 14.12.2022
 **/
class UserRepository @Inject constructor(private val userDao: UserDao){


    suspend fun createUser(user: User) = userDao.insert(user)
}