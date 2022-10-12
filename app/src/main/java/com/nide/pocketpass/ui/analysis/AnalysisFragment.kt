package com.nide.pocketpass.ui.analysis

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnLayout
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.transition.MaterialElevationScale
import com.google.android.material.transition.MaterialFadeThrough
import com.nide.pocketpass.R
import com.nide.pocketpass.data.module.Password
import com.nide.pocketpass.databinding.FragmentAnalysisBinding
import com.nide.pocketpass.util.password_util.PasswordUtil
import com.nide.pocketpass.util.collectLatestFlow
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*


@AndroidEntryPoint
class AnalysisFragment : Fragment() {

    private var _binding: FragmentAnalysisBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AnalysisViewModel by viewModels()

    private val adapter by lazy {
        SecPasswordAdapter(requireContext()) { password -> navigateToPasswordDetails(password) }
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
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAnalysisBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }
        view.doOnLayout { }
        obServeData()
        initClick()
        setupRv()
    }

    private fun initClick() {
        binding.btnAddPassword.setOnClickListener {

            val action = AnalysisFragmentDirections.actionNavigationAnalysisToNewRecordFragment()
            findNavController().navigate(action)
        }

        binding.btnProfile.setOnClickListener {
            val action = AnalysisFragmentDirections.actionNavigationAnalysisToProfileFragment()
            findNavController().navigate(action)
        }
    }


    private fun setupRv() {
        binding.rvAnalysis.adapter = adapter
    }

    private fun obServeData() {
        collectLatestFlow(viewModel.passwords) {
            adapter.submitList(it)
        }

        collectLatestFlow(viewModel.analysis) { util ->
            binding.dashboard.tvProgresText.text = "${util.totalSequre}%"
            binding.dashboard.cpAnalytics.progress = util.totalSequre.toFloat()
            binding.dashboard.tvSecureText.text = "${util.totalSequre} secure"
            binding.dashboard.tvWeekNumber.text = util.totalWeek.toString()
            binding.dashboard.tvSafeNumber.text = util.totalSafe.toString()
            binding.dashboard.tvRiskNumber.text = util.totalRisk.toString()
        }

    }

    private suspend fun setAnalysisData(passList: List<Password>) = lifecycleScope.launch {
        val util = PasswordUtil()
        val sequre = util.checkSecurePasswords(passList)
        binding.dashboard.cpAnalytics.progress = sequre.toFloat()
        binding.dashboard.tvProgresText.text = "$sequre %"
        binding.dashboard.tvSecureText.text = "$sequre secure"
        binding.dashboard.tvWeekNumber.text = util.totalWeek.toString()
        binding.dashboard.tvSafeNumber.text = util.totalSafe.toString()
        binding.dashboard.tvRiskNumber.text = util.totalRisk.toString()
    }

    private fun navigateToPasswordDetails(password: Password) {
        val action =
            AnalysisFragmentDirections.actionNavigationAnalysisToPasswordDetailsFragment(password.id)
        findNavController().navigate(action)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}