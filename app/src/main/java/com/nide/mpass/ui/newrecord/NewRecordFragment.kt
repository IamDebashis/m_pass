package com.nide.mpass.ui.newrecord

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.nide.mpass.R
import com.nide.mpass.data.module.Category
import com.nide.mpass.data.module.Password
import com.nide.mpass.databinding.FragmentNewRecordBinding
import com.nide.mpass.util.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class NewRecordFragment : Fragment() {

    private var _binding: FragmentNewRecordBinding? = null
    val binding get() = _binding!!
    private var addPasswordManualState = true

    private val viewModel: NewRecordViewModel by viewModels()


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
        observeData()
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
            val name = binding.etNameField.editText!!.text.toString()
            val password = binding.etPasswordField.editText!!.text.toString()
            val url = binding.etLinkField.editText!!.text.toString()
            val username = binding.etUseridField.editText!!.text.toString()

            viewModel.saveNewPassword(Password(0, name, username, url, password, "some", null))

        }

        binding.etNameField.editText!!.doOnTextChanged { text, start, before, count ->
            if (text!!.trim().isNotEmpty()) {
                binding.ivNameCheck.setImageResource(R.drawable.ic_green_tick_circle)
            } else {
                binding.ivNameCheck.setImageResource(R.drawable.ic_tick_circle)
            }

        }

        binding.etUseridField.editText!!.doOnTextChanged { text, start, before, count ->
            if (text!!.trim().isNotEmpty()) {
                binding.ivUseridCheck.setImageResource(R.drawable.ic_green_tick_circle)
            } else {
                binding.ivUseridCheck.setImageResource(R.drawable.ic_tick_circle)
            }

        }
        binding.etLinkField.editText!!.doOnTextChanged { text, start, before, count ->
            if (text!!.trim()
                    .isNotEmpty() && text.contains("https://") || text.contains("http://")
            ) {
                binding.ivLinkCheck.setImageResource(R.drawable.ic_green_tick_circle)
            } else {
                binding.ivLinkCheck.setImageResource(R.drawable.ic_tick_circle)
            }

        }

        binding.passwordConfig.sbLength.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
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

        binding.btnAddCategory.setOnClickListener {
            showBottomSheetDialog()
        }

    }


    private fun observeData() {
        lifecycleScope.launchWhenStarted {
            viewModel.getAllCategory().collectLatest {
                val names = it.map { category -> category.name }
                val arrayAdapter =
                    ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, names)
                (binding.etField.editText as AutoCompleteTextView).setAdapter(arrayAdapter)
                (binding.etField.editText as AutoCompleteTextView).setText(
                    arrayAdapter.getItem(0),
                    false
                )
            }
        }


    }

    private fun showBottomSheetDialog() {
        val view: View = layoutInflater.inflate(R.layout.add_category_bottom_sheet, null)
        val bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialog)
        bottomSheetDialog.setContentView(view)
        val saveBtn = bottomSheetDialog.findViewById<Button>(R.id.btn_save_category)
        val nameEt = bottomSheetDialog.findViewById<EditText>(R.id.et_category)
        saveBtn?.setOnClickListener {
            if (nameEt!!.validateCategory()) {
                viewModel.saveNewCategory(Category(0, nameEt.text.toString()))
                bottomSheetDialog.dismiss()
            }
        }
        bottomSheetDialog.show()
    }


    private fun EditText.validateCategory(): Boolean {
        return if (this.text.toString().trim().isEmpty()) {
            error = "write a name"
            false
        } else {
            error = null
            true
        }
    }
}