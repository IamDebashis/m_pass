package com.nide.pocketpass.ui

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.transition.TransitionManager
import com.google.android.material.transition.MaterialSharedAxis
import com.nide.pocketpass.R
import com.nide.pocketpass.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    val currentFragment: Fragment?
        get() = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main)
            ?.childFragmentManager
            ?.fragments
            ?.first()

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
                    showBottomNav()
                }
                R.id.navigation_analysis -> {
                    showBottomNav()
                }
                R.id.navigation_settings -> {
                    showBottomNav()
                }
                R.id.navigation_search -> {
                    currentFragment?.apply {
                        exitTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true).apply {
                            duration = resources.getInteger(R.integer.mpass_motion_duration_large).toLong()
                        }
                        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false).apply {
                            duration = resources.getInteger(R.integer.mpass_motion_duration_large).toLong()
                        }
                    }
                    hideBottomNav()
                }
                else -> {
                    hideBottomNav()
                }
            }
        }

    }

    private fun hideBottomNav() {
        val slideDown = MaterialSharedAxis(MaterialSharedAxis.Y, false).apply {
            duration = 84L
        }
        TransitionManager.beginDelayedTransition(binding.navView, slideDown)
        binding.navView.visibility = BottomNavigationView.GONE

    }

    private fun showBottomNav() {
        val slideUp = MaterialSharedAxis(MaterialSharedAxis.Y, true)
        TransitionManager.beginDelayedTransition(binding.navView, slideUp)
        binding.navView.visibility = BottomNavigationView.VISIBLE
    }


}