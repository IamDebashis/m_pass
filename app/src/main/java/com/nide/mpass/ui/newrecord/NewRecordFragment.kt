package com.nide.mpass.ui.newrecord

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.get
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.navigateUp
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.nide.mpass.R
import com.nide.mpass.data.module.Category
import com.nide.mpass.data.module.Password
import com.nide.mpass.databinding.FragmentNewRecordBinding
import com.nide.mpass.util.*
import com.nide.mpass.util.AESEncryption.encrypt
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class NewRecordFragment : Fragment() {
    private val TAG = this.javaClass.name
    private var _binding: FragmentNewRecordBinding? = null
    val binding get() = _binding!!
    private var addPasswordManualState = false

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
                binding.btnManually.text = getString(R.string.generate_password)
                binding.passwordConfig.hide()
                binding.etPassword.enableEditText(true)


            } else {
                addPasswordManualState = true
                binding.btnManually.text = getString(R.string.add_manually)
                binding.passwordConfig.show()
                val password = generatePassword()
                binding.etPassword.setText(password)
                binding.etPassword.enableEditText(false)

            }
        }


        binding.passwordConfig.setGenerateBtnClickListener {
            val password = generatePassword()
            binding.etPassword.setText(password)
        }
        binding.passwordConfig.setSaveBtnClickListener {
            addPasswordManualState = false
            binding.btnManually.text = getString(R.string.generate_password)
            binding.passwordConfig.hide()
            binding.etPassword.enableEditText(true)
        }


        binding.btnSave.setOnClickListener {

            val name = binding.etNameField.editText!!.text.toString()
            val password = binding.etPassword.getText()
            val url = binding.etLinkField.editText!!.text.toString()
            val username = binding.etUseridField.editText!!.text.toString()
            val note = binding.etNoteField.editText!!.text.toString()
            val fieldId = viewModel.fieldPosition.value
            Log.i(TAG, "initClickListener: $password")
            if (binding.etNameField.editText!!.validate() && binding.etUseridField.editText!!.validate()) {

                if (password.isNotEmpty()) {
                    val enPass = password.encrypt()
                    if (enPass != null) {
                        viewModel.saveNewPassword(
                            Password(
                                id = 0,
                                name = name,
                                userId = username,
                                password = enPass,
                                url = url,
                                categoryId = fieldId?.id,
                                notes = note
                            )
                        )
                        findNavController().navigateUp()
                    } else {
                        context?.toast("Something went wrong, please try again")
                    }

                } else {
                    viewModel.saveNewPassword(
                        Password(
                            id = 0,
                            name = name,
                            userId = username,
                            password = password,
                            url = url,
                            categoryId = fieldId?.id,
                            notes = note
                        )
                    )
                    findNavController().navigateUp()
                }


            }


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


        binding.btnAddCategory.setOnClickListener {
            showBottomSheetDialog()
        }


    }

    private fun generatePassword(): String {
        return PasswordBuilder(binding.passwordConfig.getLenth())
            .isDigit(binding.passwordConfig.getNumberCheck())
            .isUpperCase(binding.passwordConfig.getUppercaseCheck())
            .isLowerCase(binding.passwordConfig.getLowercaseCheck())
            .isSpecialChar(binding.passwordConfig.getSymbolCheck())
            .build()
            .generatePassword()
    }


    private fun observeData() {
        lifecycleScope.launchWhenStarted {
            viewModel.getAllCategory().collectLatest { categories ->
                val names = categories.map { it.categoryName }
                val arrayAdapter =
                    ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, names)
                val autoCompleteTextView = (binding.etField.editText as AutoCompleteTextView)
                autoCompleteTextView.setAdapter(arrayAdapter)
                autoCompleteTextView.setText(arrayAdapter.getItem(0), false)
                viewModel.fieldPosition.postValue(categories[0])
                autoCompleteTextView.onItemClickListener =
                    AdapterView.OnItemClickListener { parent, view, position, id ->
                        val item = categories[position]
                        viewModel.fieldPosition.postValue(item)
                    }
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
            if (nameEt!!.validate()) {
                viewModel.saveNewCategory(Category(0, nameEt.text.trim().toString()))
                bottomSheetDialog.dismiss()
            }
        }
        bottomSheetDialog.show()
    }


    private fun EditText.validate(): Boolean {
        return if (this.text.toString().trim().isEmpty()) {
            error = "Field can't be empty"
            false
        } else {
            error = null
            true
        }
    }
}