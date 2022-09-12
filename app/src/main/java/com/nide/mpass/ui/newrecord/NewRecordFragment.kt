package com.nide.mpass.ui.newrecord

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.google.android.material.transition.platform.MaterialSharedAxis
import com.nide.mpass.R
import com.nide.mpass.databinding.FragmentNewRecordBinding
import com.nide.mpass.util.hide
import com.nide.mpass.util.show
import com.nide.mpass.util.toast


class NewRecordFragment : Fragment() {

    private var _binding: FragmentNewRecordBinding? = null
    val binding get() = _binding!!
    private var addPasswordManualState = true


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.nav_host_fragment_activity_main
            duration = 300
            scrimColor = Color.TRANSPARENT
            setAllContainerColors(requireContext().getColor(R.color.white))
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNewRecordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClickListener()

    }


    private fun initClickListener() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnManually.setOnClickListener {

            if (addPasswordManualState) {
                addPasswordManualState = false
                binding.btnManually.text = "Generate Password"
                binding.passwordConfigContainer.hide()
                binding.etPasswordField.editText!!.isEnabled = true
                binding.etPasswordField.isEndIconVisible = false


            } else {
                addPasswordManualState = true
                binding.btnManually.text = "Add Manually"
                binding.passwordConfigContainer.show()
                binding.etPasswordField.editText!!.isEnabled = false
                binding.etPasswordField.isEndIconVisible = true

            }
        }

        binding.etPasswordField.setEndIconOnClickListener {
            context?.toast("Refresh")
        }

        binding.btnSave.setOnClickListener {
            context?.toast("Save")
        }

        binding.etNameField.editText!!.doOnTextChanged { text, start, before, count ->
            if(text!!.trim().isNotEmpty()){
                binding.ivNameCheck.setImageResource(R.drawable.ic_green_tick_circle)
            }else{
                binding.ivNameCheck.setImageResource(R.drawable.ic_tick_circle)
            }

        }

        binding.etUseridField.editText!!.doOnTextChanged { text, start, before, count ->
            if(text!!.trim().isNotEmpty()){
                binding.ivUseridCheck.setImageResource(R.drawable.ic_green_tick_circle)
            }else{
                binding.ivUseridCheck.setImageResource(R.drawable.ic_tick_circle)
            }

        }
        binding.etLinkField.editText!!.doOnTextChanged { text, start, before, count ->
            if(text!!.trim().isNotEmpty()&& text.contains("https://") || text.contains("http://")){
                binding.ivLinkCheck.setImageResource(R.drawable.ic_green_tick_circle)
            }else{
                binding.ivLinkCheck.setImageResource(R.drawable.ic_tick_circle)
            }

        }

        binding.passwordConfig.sbLength.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.passwordConfig.tvLengthCount.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }


        })

        binding.passwordConfig.btnSave.setOnClickListener {
            context?.toast("password Save")
        }

        binding.passwordConfig.btnRegenerate.setOnClickListener {
            context?.toast("password Regenerate")
        }

    }


}