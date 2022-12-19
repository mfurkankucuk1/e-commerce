package com.mfk.roomexample.di

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mfk.roomexample.data.model.Cart
import com.mfk.roomexample.data.remote.CartDao

/**
 * Created by M.Furkan KÜÇÜK on 19.12.2022
 **/
@Database(entities = [Cart::class], version = 1)
abstract class CartDatabase : RoomDatabase() {
    abstract fun getCartDao(): CartDao
}