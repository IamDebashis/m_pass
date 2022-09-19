package com.nide.mpass.ui.viewpassword

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.transition.MaterialContainerTransform
import com.google.android.material.transition.platform.MaterialFadeThrough
import com.google.android.material.transition.platform.MaterialSharedAxis
import com.nide.mpass.R
import com.nide.mpass.databinding.FragmentPasswordDetailsBinding
import com.nide.mpass.util.AvaterCreator
import com.nide.mpass.util.PasswordStrength
import com.nide.mpass.util.RandomColors
import com.nide.mpass.util.setProgressWithColor
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class PasswordDetailsFragment : Fragment() {

    private var _binding: FragmentPasswordDetailsBinding? = null
    private val binding get() = _binding!!

    val args: PasswordDetailsFragmentArgs by navArgs()

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

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        lifecycleScope.launchWhenStarted {
            viewModel.getPasswordById(args.passwordId).collectLatest {
                binding.apply {
                    tvTitle.text = it.name
                    tvUserName.text = it.userId
                    tvUserId.text = it.userId
                    password.etPasswordBox.isEndIconVisible = !it.password.isNullOrBlank()
                    password.etPasswordBox.editText?.setText(it.password?.ifBlank { "(no password)" })
                    it.password?.let { pass ->
                        password.progressPasswordStrength.setProgressWithColor(PasswordStrength(pass).check())
                    }
                    tvLink.text = it.url?.ifEmpty { "no link" }
                    tvField.text = it.categoryName
                    tvNote.text = it.notes?.ifEmpty { "No notes" }
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

            }
        }
        /*viewModel.getPasswordById(args.passwordId).asLiveData().observe(viewLifecycleOwner) {
            binding.apply {
                tvTitle.text = it.name
                tvUserName.text = it.userId
                tvUserId.text = it.userId
                tvPassword.text = it.password
                tvLink.text = it.url
            }
        }*/

    }


}