package com.nide.mpass.ui.update

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.nide.mpass.R
import com.nide.mpass.data.module.Category
import com.nide.mpass.data.module.Password
import com.nide.mpass.databinding.FragmentUpdateBinding
import com.nide.mpass.password_util.PasswordBuilder
import com.nide.mpass.util.AESEncryption.decrypt
import com.nide.mpass.util.AESEncryption.encrypt
import com.nide.mpass.util.hide
import com.nide.mpass.util.show
import com.nide.mpass.util.toast
import com.nide.mpass.util.validate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UpdateFragment : Fragment() {

    private var _binding: FragmentUpdateBinding? = null
    val binding get() = _binding!!

    private val args by navArgs<UpdateFragmentArgs>()
    private val viewModel: UpdateViewModel by viewModels()
    private var addPasswordManualState = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()
        initClick()
    }

    private fun observeData() {
        lifecycleScope.launchWhenStarted {

            launch {
                viewModel.getAllCategory().collectLatest { categories ->
                    val names = categories.map { it.categoryName }
                    val arrayAdapter =
                        ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, names)
                    val autoCompleteTextView = (binding.etField.editText as AutoCompleteTextView)
                    autoCompleteTextView.setAdapter(arrayAdapter)
                    autoCompleteTextView.onItemClickListener =
                        AdapterView.OnItemClickListener { parent, view, position, id ->
                            val item = categories[position]
                            viewModel.fieldPosition.postValue(item)
                        }
                }
            }
            launch {
                viewModel.getPassword(args.passId).collectLatest { password ->
                    binding.apply {
                        etNameField.editText?.setText(password.name)
                        etUseridField.editText?.setText(password.userId)
                        etLinkField.editText?.setText(password.url)
                        password.password?.let { etPassword.setText(it.decrypt()?:"") }
                        etNoteField.editText?.setText(password.notes)
                    }
                    viewModel.fieldPosition.postValue(
                        Category(
                            password.categoryId!!,
                            password.categoryName
                        )
                    )
                    val autoCompleteTextView = (binding.etField.editText as AutoCompleteTextView)
                    autoCompleteTextView.setText(password.categoryName, false)
                }
            }

        }

    }

    private fun  initClick(){

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnUpdate.setOnClickListener {

            val name = binding.etNameField.editText!!.text.toString()
            val password = binding.etPassword.getText()
            val url = binding.etLinkField.editText!!.text.toString()
            val username = binding.etUseridField.editText!!.text.toString()
            val note = binding.etNoteField.editText!!.text.toString()
            val fieldId = viewModel.fieldPosition.value
            if (binding.etNameField.editText!!.validate() && binding.etUseridField.editText!!.validate()) {

                if (password.isNotEmpty()) {
                    val enPass = password.encrypt()
                    if (enPass != null) {
                        viewModel.updatePassword(
                            Password(
                                id = args.passId,
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
                    viewModel.updatePassword(
                        Password(
                            id = args.passId,
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}