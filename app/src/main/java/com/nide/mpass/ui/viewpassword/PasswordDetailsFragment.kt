package com.nide.mpass.ui.viewpassword

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.nide.mpass.databinding.FragmentPasswordDetailsBinding
import com.nide.mpass.password_util.PasswordStrength
import com.nide.mpass.util.*
import com.nide.mpass.util.AESEncryption.decrypt
import com.nide.mpass.util.RandomColors
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

        /* sharedElementEnterTransition = MaterialContainerTransform().apply {
             drawingViewId = R.id.nav_host_fragment_activity_main
             duration = 300
             scrimColor = Color.WHITE
             setAllContainerColors(requireContext().getColor(R.color.white))
         }*/

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPasswordDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
                val image = AvaterCreator.AvatarBuilder(requireContext())
                    .setLabel(it.name)
                    .setAvatarSize(66)
                    .setTextSize(14)
                    .toCircle()
                    .setBackgroundColor(RandomColors().getColor())
                    .build()

                binding.ivIcon.setImageDrawable(image)
                binding.btnCopyPassword.setOnClickListener { _->
                   context?.copyToClipBoard("password",it.password?.decrypt()?:"")
                    context?.toast("password copied ")
                }

            }
        }

    }


}