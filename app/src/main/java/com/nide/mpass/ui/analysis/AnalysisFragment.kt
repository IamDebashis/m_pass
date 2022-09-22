package com.nide.mpass.ui.analysis

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.nide.mpass.R
import com.nide.mpass.data.module.Password
import com.nide.mpass.databinding.FragmentAnalysisBinding
import com.nide.mpass.password_util.PasswordUtil
import com.nide.mpass.ui.home.HomeFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class AnalysisFragment : Fragment() {

    private var _binding: FragmentAnalysisBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AnalysisViewModel by viewModels()

    private val adapter = SecPasswordAdapter { password -> navigateToPasswordDetails(password) }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val analysisViewModel =
            ViewModelProvider(this)[AnalysisViewModel::class.java]

        _binding = FragmentAnalysisBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRv()
        obServeData()
        initClick()
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

        lifecycleScope.launchWhenStarted {
            viewModel.passwords.collectLatest {
                adapter.submitList(it)
                async {
                    setAnalysisData(it)
                }
            }
        }
    }

    private suspend fun setAnalysisData(passList: List<Password>) = withContext(Default) {
        val util = PasswordUtil()
        val sequre = util.checkSequrePasswords(passList)
        launch(Main) {
            binding.dashboard.cpAnalytics.progress = sequre.toFloat()
            binding.dashboard.tvProgresText.text = "$sequre %"
            binding.dashboard.tvSecureText.text = "$sequre secure"
            binding.dashboard.tvWeekNumber.text = util.totalWeek.toString()
            binding.dashboard.tvSafeNumber.text = util.totalSafe.toString()
            binding.dashboard.tvRiskNumber.text = util.totalRisk.toString()
        }
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