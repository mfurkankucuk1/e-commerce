package com.mfk.roomexample.di

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mfk.roomexample.data.model.Cart
import com.mfk.roomexample.data.model.Favorite
import com.mfk.roomexample.data.model.Product
import com.mfk.roomexample.data.model.User
import com.mfk.roomexample.data.remote.DatabaseDao

/**
 * Created by M.Furkan KÜÇÜK on 7.12.2022
 **/
@Database(entities = [Product::class, Cart::class,User::class,Favorite::class], version = 1)
abstract class ApplicationDatabase : RoomDatabase() {
    abstract fun getDao(): DatabaseDao
}