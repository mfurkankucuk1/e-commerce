package com.mfk.roomexample

import android.app.Application
import com.facebook.stetho.Stetho
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * Created by M.Furkan KÜÇÜK on 7.12.2022
 **/
@HiltAndroidApp
class RoomApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
            Timber.plant(Timber.DebugTree())
        }
    }
}