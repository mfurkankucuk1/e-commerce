package com.mfk.roomexample.di

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mfk.roomexample.data.model.User
import com.mfk.roomexample.data.remote.UserDao

/**
 * Created by M.Furkan KÜÇÜK on 14.12.2022
 **/

@Database(entities = [User::class], version = 1)
abstract class UserDatabase : RoomDatabase() {
    abstract fun getDao(): UserDao
}