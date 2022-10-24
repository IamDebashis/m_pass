package com.nide.pocketpass.ui.about

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.nide.pocketpass.R
import com.nide.pocketpass.databinding.FragmentAboutBinding

class AboutFragment : Fragment(R.layout.fragment_about) {

    lateinit var binding : FragmentAboutBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAboutBinding.bind(view)

        binding.btnClose.setOnClickListener {
            findNavController().navigateUp()
        }

    }






}