package com.nide.mpass.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nide.mpass.R
import com.nide.mpass.databinding.FragmentPasswordDetailsBinding


class PasswordDetailsFragment : Fragment() {

    private var _binding: FragmentPasswordDetailsBinding? = null
    val binding get() = _binding!!

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPasswordDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }


}