package com.nide.pocketpass

import android.app.Application
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.nide.pocketpass.data.module.User
import com.nide.pocketpass.data.remote.util.DatabaseConstant
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
