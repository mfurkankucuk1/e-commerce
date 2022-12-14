package com.mfk.roomexample.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.mfk.roomexample.data.remote.ProductDao
import com.mfk.roomexample.data.remote.UserDao
import com.mfk.roomexample.data.repository.PreferencesRepository
import com.mfk.roomexample.utils.Constants
import com.mfk.roomexample.utils.Constants.PRODUCT_DATABASE_NAME
import com.mfk.roomexample.utils.Constants.USER_DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by M.Furkan KÜÇÜK on 7.12.2022
 **/
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): ProductDatabase {
        return Room.databaseBuilder(context,ProductDatabase::class.java,PRODUCT_DATABASE_NAME).allowMainThreadQueries().build()
    }

    @Singleton
    @Provides
    fun provideProductDao(appDatabase: ProductDatabase): ProductDao {
        return appDatabase.getDao()
    }

    @Singleton
    @Provides
    fun provideUserDatabase(@ApplicationContext context: Context): UserDatabase {
        return Room.databaseBuilder(context,UserDatabase::class.java,USER_DATABASE_NAME).allowMainThreadQueries().build()
    }

    @Singleton
    @Provides
    fun provideUserDao(appDatabase: UserDatabase): UserDao {
        return appDatabase.getDao()
    }

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(Constants.PREFERENCES_NAME, Context.MODE_PRIVATE)

    @Singleton
    @Provides
    fun providePreferencesHelper(sharedPreferences: SharedPreferences): PreferencesRepository =
        PreferencesRepository(sharedPreferences)


}