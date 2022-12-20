package com.mfk.roomexample.data.repository

import com.mfk.roomexample.data.model.User
import com.mfk.roomexample.data.remote.DatabaseDao
import javax.inject.Inject

/**
 * Created by M.Furkan KÜÇÜK on 14.12.2022
 **/
class UserRepository @Inject constructor(private val userDao: DatabaseDao){

    suspend fun createUser(user: User) = userDao.insert(user)

    fun getUser(id:String) = userDao.getUser(id)

    fun getUserForLogin(userNameOrEmail: String, password: String) = userDao.getUserForLogin(userNameOrEmail, password)

    fun loginUser(userNameOrEmail: String, password: String) = userDao.loginUser(userNameOrEmail,password)

}