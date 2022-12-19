package com.mfk.roomexample.di

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mfk.roomexample.data.model.Favorite
import com.mfk.roomexample.data.model.Product
import com.mfk.roomexample.data.remote.FavoriteDao
import com.mfk.roomexample.data.remote.ProductDao

/**
 * Created by M.Furkan KÜÇÜK on 19.12.2022
 **/

@Database(entities = [Favorite::class], version = 1)
abstract class FavoriteDatabase : RoomDatabase() {
    abstract fun getFavoriteDao(): FavoriteDao
}