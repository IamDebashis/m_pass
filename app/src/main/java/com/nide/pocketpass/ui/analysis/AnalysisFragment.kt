package com.nide.pocketpass.ui.analysis

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.nide.pocketpass.R
import com.nide.pocketpass.data.module.Password
import com.nide.pocketpass.databinding.FilterBottmSheetBinding
import com.nide.pocketpass.databinding.FragmentAnalysisBinding
import com.nide.pocketpass.util.collectLatestFlow
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AnalysisFragment : Fragment() {

    private var _binding: FragmentAnalysisBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AnalysisViewModel by viewModels()

    private val adapter by lazy {
        SecPasswordAdapter(requireContext()) { password -> navigateToPasswordDetails(password) }
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

        binding.btnSort.setOnClickListener {
            showSortBottomSheet()
        }

    }


    private fun setupRv() {
        binding.rvAnalysis.adapter = adapter
    }

    private fun obServeData() {
        collectLatestFlow(viewModel.sortPassword) {
            binding.llNoPassword.isVisible = it.isEmpty()
            binding.rvAnalysis.isVisible = it.isNotEmpty()
            adapter.submitList(it)
        }

        collectLatestFlow(viewModel.analysis) { util ->

            Log.i("TAG", "obServeData:${util.getTotalPassword().toFloat()} , ${util.totalSecure.toFloat()} : ${util.totalWeek} ${util.totalSafe} ${util.totalRisk} ${util.getSecurePass()}")
            binding.dashboard.cpAnalytics.progressMax = 100.toFloat()
            binding.dashboard.cpAnalytics.progress = util.getSecurePass().toFloat()
            binding.dashboard.tvProgresText.text = "${util.getSecurePass()}%"
            binding.dashboard.tvSecureText.text = "${util.totalSecure} secure"
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

    private fun showSortBottomSheet() {
        val view = FilterBottmSheetBinding.inflate(layoutInflater)

        val bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialog)
        bottomSheetDialog.setContentView(view.root)

        view.filterGroup.setOnCheckedChangeListener { _, checkedId ->
            when(checkedId){
                R.id.rb_all ->{
                    viewModel.setFilterRange(0..100)
                    bottomSheetDialog.dismiss()
                }
                R.id.rb_safe ->{
                    viewModel.setFilterRange(61..100)
                    bottomSheetDialog.dismiss()
                }
                R.id.rb_risk ->{
                    viewModel.setFilterRange(1..30)
                    bottomSheetDialog.dismiss()
                }
                R.id.rb_week ->{
                    viewModel.setFilterRange(31..60)
                    bottomSheetDialog.dismiss()
                }
            }
        }

        collectLatestFlow(viewModel.filterBy){ range->
            when(range){
                0..100 -> view.rbAll.isChecked = true
                1..30 -> view.rbRisk.isChecked = true
                31..60 -> view.rbWeek.isChecked = true
                61..100 -> view.rbSafe.isChecked = true
            }
        }

        bottomSheetDialog.show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}