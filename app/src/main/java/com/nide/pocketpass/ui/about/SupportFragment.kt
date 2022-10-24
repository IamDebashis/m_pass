package com.nide.pocketpass.ui.about

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.method.LinkMovementMethod
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import com.nide.pocketpass.R
import com.nide.pocketpass.databinding.FragmentSupportBinding


class SupportFragment : Fragment(R.layout.fragment_support) {

    lateinit var binding: FragmentSupportBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSupportBinding.bind(view)

        binding.tvLink.movementMethod = LinkMovementMethod.getInstance()

        binding.btnClose.setOnClickListener {
            findNavController().navigateUp()
        }

        initClicks()
    }

    private fun initClicks() = binding.apply {


        ivLinkedin.setOnClickListener {
            val linkedin = resources.getString(R.string.linkedin_profile)
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(linkedin)).apply {
                `package` = "com.linkedin.android"
            }
            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Intent(Intent.ACTION_VIEW, Uri.parse(linkedin)).apply {
                    startActivity(this)
                }
            }

        }
        ivGithub.setOnClickListener {

            val github = resources.getString(R.string.github_profile)
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(github)).apply {
                `package` = "com.github.android"
            }
            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Intent(Intent.ACTION_VIEW, Uri.parse(github)).apply {
                    startActivity(this)
                }
            }

        }
        ivTwitter.setOnClickListener {

            val twitter = resources.getString(R.string.twitter_profile)
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(twitter)).apply {
                `package` = "com.twitter.android"
            }
            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Intent(Intent.ACTION_VIEW, Uri.parse(twitter)).apply {
                    startActivity(this)
                }
            }

        }

    }


}