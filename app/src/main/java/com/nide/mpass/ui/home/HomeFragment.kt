package com.nide.mpass.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.google.android.material.transition.MaterialSharedAxis
import com.google.android.material.transition.platform.MaterialElevationScale
import com.nide.mpass.R
import com.nide.mpass.data.module.Password
import com.nide.mpass.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel by viewModels<HomeViewModel>()
    private val adapter =
        PasswordAdapter { password -> onItemClick(password) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAddPassword.setOnClickListener {
            val extras =
                FragmentNavigatorExtras(binding.btnAddPassword to "shared_element_container")
            findNavController().navigate(
                R.id.action_navigation_home_to_newRecordFragment,
                null,
                null,
                extras
            )

        }

        binding.btnProfile.setOnClickListener {
            val action = HomeFragmentDirections.actionNavigationHomeToProfileFragment()
            findNavController().navigate(action)
        }

        initRV()

    }

    fun initRV() {
        binding.rvPassword.adapter = adapter
        observeFlow()
    }

    fun observeFlow() {
        lifecycleScope.launchWhenStarted {
            homeViewModel.passwords.collectLatest {
                adapter.submitList(it)
            }
        }
    }

    private fun onItemClick(password: Password) {
        exitTransition = MaterialElevationScale(false).apply {
            duration = resources.getInteger(
                R.integer.reply_motion_duration_large
            ).toLong()
        }
        reenterTransition = MaterialElevationScale(true).apply {
            duration = resources.getInteger(
                R.integer.reply_motion_duration_large
            ).toLong()
        }
        val transitionName = getString(R.string.password_details_transaction)
        val passwordDetailsImage = getString(R.string.password_details_image_transaction)

        val action =
            HomeFragmentDirections.actionNavigationHomeToPasswordDetailsFragment(password.id)
        findNavController().navigate(action)


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}