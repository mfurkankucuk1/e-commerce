package com.mfk.roomexample.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.mfk.roomexample.data.remote.DatabaseDao
import com.mfk.roomexample.data.repository.PreferencesRepository
import com.mfk.roomexample.utils.Constants
import com.mfk.roomexample.utils.Constants.PRODUCT_DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import timber.log.Timber
import java.util.concurrent.Executors
import javax.inject.Singleton

/**
 * Created by M.Furkan KÜÇÜK on 7.12.2022
 **/
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): ApplicationDatabase {

        val dbBuilder = Room.databaseBuilder(
            context, ApplicationDatabase::class.java, PRODUCT_DATABASE_NAME
        )
        dbBuilder.setQueryCallback({ sqlQuery, bindArgs ->
            Timber.e("SQL QUERY: $sqlQuery -----> SQL Args: $bindArgs")
        }, Executors.newSingleThreadExecutor())
        return dbBuilder.allowMainThreadQueries().build()
    }

    @Singleton
    @Provides
    fun provideProductDao(appDatabase: ApplicationDatabase): DatabaseDao {
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