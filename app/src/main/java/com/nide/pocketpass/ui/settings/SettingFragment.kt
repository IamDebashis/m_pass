package com.nide.pocketpass.ui.settings

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.autofill.AutofillManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.transition.MaterialElevationScale
import com.google.android.material.transition.MaterialFadeThrough
import com.nide.pocketpass.R
import com.nide.pocketpass.databinding.FragmentSettingBinding
import dagger.hilt.android.AndroidEntryPoint
import org.jetbrains.annotations.Contract


@AndroidEntryPoint
class SettingFragment : Fragment() {
    private val TAG = javaClass.simpleName

    private var _binding: FragmentSettingBinding? = null
    val binding get() = _binding!!
    private lateinit var viewModel: SettingViewModel

    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val afm = requireActivity().getSystemService(AutofillManager::class.java)
            binding.switchAutofill.isChecked = afm.hasEnabledAutofillServices()
        }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialFadeThrough().apply {
            duration = resources.getInteger(R.integer.mpass_motion_duration_large).toLong()
        }
        exitTransition = MaterialElevationScale(false).apply {
            duration = resources.getInteger(R.integer.mpass_motion_duration_large).toLong()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initClick()
    }


    private fun initViews() = binding.apply {
        val afm = requireContext().getSystemService(AutofillManager::class.java)
        switchAutofill.isChecked = afm.hasEnabledAutofillServices()
    }

    private fun initClick() = binding.apply {
        btnAddPassword.setOnClickListener {
            val action = SettingFragmentDirections.actionNavigationSettingsToNewRecordFragment()
            findNavController().navigate(action)
        }

        btnEditProfile.setOnClickListener {
            val action = SettingFragmentDirections.actionNavigationSettingsToProfileFragment()
            findNavController().navigate(action)
        }

        switchAutofill.setOnClickListener {
            val afm = requireContext().getSystemService(AutofillManager::class.java)

            if (afm.hasEnabledAutofillServices()) {
                val intent = Intent(Settings.ACTION_REQUEST_SET_AUTOFILL_SERVICE)
                intent.data = Uri.parse("package:none")
                startForResult.launch(intent)
            } else {
                val intent = Intent(Settings.ACTION_REQUEST_SET_AUTOFILL_SERVICE)
                intent.data = Uri.parse("package:com.nide.pocketpass")
                startForResult.launch(intent)
            }


        }


    }


}