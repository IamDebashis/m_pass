package com.nide.pocketpass.ui.profile

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.MaterialSharedAxis
import com.nide.pocketpass.R
import com.nide.pocketpass.databinding.FragmentProfileBinding
import com.nide.pocketpass.util.collectLatestFlow
import com.nide.pocketpass.util.isNetworkAvailable
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    val binding get() = _binding!!

    private val viewModel: ProfileViewModel by viewModels()


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Y, true).apply {
            duration = resources.getInteger(R.integer.mpass_motion_duration_medium).toLong()
        }
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.Y, false).apply {
            duration = resources.getInteger(R.integer.mpass_motion_duration_large).toLong()
        }
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Y, true).apply {
            duration = resources.getInteger(R.integer.mpass_motion_duration_large).toLong()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClick()
        initViews()
    }

    private fun initViews() = binding.apply {
        etEmail.addTextChangedListener(watcher)
        etName.addTextChangedListener(watcher)
        collectLatestFlow(viewModel.userName) {
            etName.setText(it)
        }
        collectLatestFlow(viewModel.userPhone) {
            etPhone.setText(it)
        }
        collectLatestFlow(viewModel.userEmail) {
            etEmail.setText(it)
        }
        collectLatestFlow(viewModel.countryCode) {

            tvCountryCode.text = resources.getString(R.string.country_code,it)
        }

    }

    private fun initClick() = binding.apply {
        binding.btnClose.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnDismiss.setOnClickListener {
            findNavController().navigateUp()
        }

        btnUpdate.setOnClickListener {
            if (isNetworkAvailable(requireContext())) {
                viewModel.updateUser(
                    requireContext(),
                    etName.text.toString(),
                    etEmail.text.toString()
                )
            } else {
                Snackbar.make(it, "you don't have any internet connection !", Snackbar.LENGTH_SHORT)
                    .apply {
                        show()
                    }
            }
        }


    }

    private val watcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable?) {
            lifecycleScope.launch {
                binding.btnUpdate.isEnabled =
                    s.toString()
                        .isNotEmpty() && (s.toString() != viewModel.userName.first() && s.toString() != viewModel.userEmail.first())
            }
        }
    }

}