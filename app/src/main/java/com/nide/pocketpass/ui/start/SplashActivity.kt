package com.nide.pocketpass.ui.start

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.nide.pocketpass.R
import com.nide.pocketpass.data.local.util.LocalDataStore
import com.nide.pocketpass.data.local.util.userDataStore
import com.nide.pocketpass.databinding.ActivitySplashBinding
import com.nide.pocketpass.ui.MainActivity
import com.nide.pocketpass.ui.start.login.LogInActivity
import kotlinx.coroutines.flow.map

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)



        Handler(Looper.getMainLooper()).postDelayed(
            {
                lifecycleScope.launchWhenStarted {
                    checkUserLogIn()
                }
            }, 1000
        )

    }

    private suspend fun checkUserLogIn() {
        val isUserLogin = userDataStore.data.map { pref ->
            pref[LocalDataStore.isUserLogin_Key] ?: false
        }
        isUserLogin.collect { login ->
            if (login) {
                Intent(this@SplashActivity, MainActivity::class.java).apply {
                    startActivity(this)
                    finish()
                }
            } else {
                Intent(this@SplashActivity, LogInActivity::class.java).apply {
                    startActivity(this)
                    finish()
                }
            }
        }
    }
}