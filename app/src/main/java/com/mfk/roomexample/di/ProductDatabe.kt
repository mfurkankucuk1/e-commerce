package com.mfk.roomexample.di

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mfk.roomexample.data.model.Product
import com.mfk.roomexample.data.remote.ProductDao

/**
 * Created by M.Furkan KÜÇÜK on 7.12.2022
 **/
@Database(entities = [Product::class], version = 1)
abstract class ProductDatabase : RoomDatabase() {
    abstract fun getDao(): ProductDao
}