package com.nide.mpass.ui.settings

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nide.mpass.R
import com.nide.mpass.ui.home.HomeViewModel

class SettingFragment : Fragment() {

    companion object {
        fun newInstance() = SettingFragment()
    }

    private lateinit var viewModel: SettingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val settingViewModel = ViewModelProvider(this)[SettingViewModel::class.java]
        return inflater.inflate(R.layout.fragment_setting, container, false)

    }


}