package com.nide.pocketpass

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()


    }

    companion object {
        @Volatile
        private var Instance: MyApplication? = null

        fun getInstance(): MyApplication {
            synchronized(this) {
                if (Instance == null) {
                    Instance = MyApplication()
                }
            }
            return Instance!!
        }
    }


}
