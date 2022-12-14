package com.mfk.roomexample

import android.app.Application
import com.airbnb.lottie.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * Created by M.Furkan KÜÇÜK on 7.12.2022
 **/
@HiltAndroidApp
class RoomApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
    }
}