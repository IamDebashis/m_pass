package com.nide.pocketpass.ui.viewpassword

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.doOnPreDraw
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.transition.MaterialSharedAxis
import com.nide.pocketpass.R
import com.nide.pocketpass.databinding.DeleteDialougeLayoutBinding
import com.nide.pocketpass.databinding.FilterBottmSheetBinding
import com.nide.pocketpass.databinding.FragmentPasswordDetailsBinding
import com.nide.pocketpass.util.password_util.PasswordStrength
import com.nide.pocketpass.util.*
import com.nide.pocketpass.util.AESEncryption.decrypt
import com.nide.pocketpass.util.RandomColors
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class PasswordDetailsFragment : Fragment() {

    private var _binding: FragmentPasswordDetailsBinding? = null
    private val binding get() = _binding!!

    private val args: PasswordDetailsFragmentArgs by navArgs()

    private val viewModel: PasswordDetailsViewModel by viewModels()

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentPasswordDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      /*  postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }*/
        initClick()
        observeData()

    }

    private fun initClick(){

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnEditPassword.setOnClickListener {
            val action = PasswordDetailsFragmentDirections.actionPasswordDetailsFragmentToUpdateFragment(args.passwordId)
            findNavController().navigate(action)
        }

        binding.btnTrash.setOnClickListener {
            showDeleteBottomSheet()
        }


    }

    private fun showDeleteBottomSheet() {
        val view = DeleteDialougeLayoutBinding.inflate(layoutInflater)
        val bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialog)
        bottomSheetDialog.setContentView(view.root)
        view.btnNegative.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
        view.btnPosetive.setOnClickListener {
            findNavController().navigateUp()
          viewModel.deletePassword(args.passwordId)
            bottomSheetDialog.dismiss()
        }
        bottomSheetDialog.show()
    }

    private fun observeData(){

        lifecycleScope.launchWhenStarted {
            viewModel.getPasswordById(args.passwordId).collectLatest {
                binding.apply {
                    tvTitle.text = it.name
                    tvUserName.text = it.userId
                    tvUserId.text = it.userId
                    password.etPasswordBox.isEndIconVisible = !it.password.isNullOrBlank()
                    password.etPasswordBox.editText?.hint = "(No Password)"
                    password.etPasswordBox.editText?.setText(it.password?.decrypt())
                    it.password?.let { pass ->
                        password.progressPasswordStrength.setProgressWithColor(PasswordStrength(pass.decrypt()?:"").check())
                    }
                    tvLink.text = it.url?.ifEmpty { "(no link)" }
                    tvField.text = it.categoryName
                    tvNote.text = it.notes?.ifEmpty { "(No notes)" }
                    btnCopyPassword.isVisible = !it.password.isNullOrEmpty()
                }
                Glide.with(binding.ivIcon)
                    .load(Const.LOGO_URL+it.name )
                    .placeholder(BitmapDrawable(binding.ivIcon.resources, it.icon))
                    .error(it.icon)
                    .into(binding.ivIcon)

                binding.btnCopyPassword.setOnClickListener { _->
                   context?.copyToClipBoard("password",it.password?.decrypt()?:"")
                    context?.toast("password copied ")
                }

            }
        }

    }


}