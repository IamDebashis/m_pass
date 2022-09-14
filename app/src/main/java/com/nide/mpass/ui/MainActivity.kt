package com.nide.mpass.ui

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.nide.mpass.R
import com.nide.mpass.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.Executors

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)


        navView.setupWithNavController(navController)

        //hide bottom navigation bar on screen change
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_home -> {
                    navView.visibility = BottomNavigationView.VISIBLE
                }
                R.id.navigation_analysis -> {
                    navView.visibility = BottomNavigationView.VISIBLE
                }
                R.id.navigation_settings -> {
                    navView.visibility = BottomNavigationView.VISIBLE
                }
                else -> {
                    navView.visibility = BottomNavigationView.GONE
                }
            }
        }


    }
}