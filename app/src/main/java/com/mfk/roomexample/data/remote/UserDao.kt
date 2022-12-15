package com.mfk.roomexample.data.remote

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mfk.roomexample.data.model.User

/**
 * Created by M.Furkan KÜÇÜK on 14.12.2022
 **/
@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User): Long

    @Query("SELECT * FROM User WHERE id=:id")
    fun getUser(id:String):User

    @Query("SELECT * FROM User WHERE(userName=:userNameOrEmail OR email=:userNameOrEmail) AND password=:password")
    fun getUserForLogin(userNameOrEmail: String, password: String):User

    @Query("SELECT CASE WHEN EXISTS (SELECT * FROM User WHERE (userName=:userNameOrEmail OR email=:userNameOrEmail) AND password=:password ) THEN 1 ELSE 0 END")
    fun loginUser(userNameOrEmail: String, password: String):Boolean
}