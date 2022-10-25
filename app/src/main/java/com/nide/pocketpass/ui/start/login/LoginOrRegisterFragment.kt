package com.nide.pocketpass.ui.start.login

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.nide.pocketpass.R
import com.nide.pocketpass.data.remote.util.DatabaseConstant
import com.nide.pocketpass.databinding.FragmentLoginOrRegisterBinding
import com.nide.pocketpass.util.validName
import com.nide.pocketpass.util.validPhone
import java.util.concurrent.TimeUnit

class LoginOrRegisterFragment : Fragment() {
    private val TAG = javaClass.simpleName

    private var _binding: FragmentLoginOrRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var firebaseAuth: FirebaseAuth
    private var db: FirebaseFirestore = Firebase.firestore

    companion object {
        var verificationId = ""
            private set
        var verificationCode = ""
            private set
        var isLogin = true
            private set
        var userName = ""
            private set
        var countryCode = ""
            private set
        var phone = ""
            private set
    }

    private var mCallback: PhoneAuthProvider.OnVerificationStateChangedCallbacks =
        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(authCredential: PhoneAuthCredential) {
                verificationCode = authCredential.smsCode ?: ""
                Log.i(TAG, "onVerificationCompleted: ${authCredential.smsCode}")

            }

            override fun onVerificationFailed(e: FirebaseException) {
                Log.i(TAG, "onVerificationFailed: ${e.message} :=> $e")
                Toast.makeText(context, e.localizedMessage, Toast.LENGTH_SHORT).show()
            }


            override fun onCodeSent(
                verificationid: String,
                forceResendingToken: PhoneAuthProvider.ForceResendingToken
            ) {
                super.onCodeSent(verificationid, forceResendingToken)
                verificationId = verificationid
                findNavController().navigate(R.id.action_loginOrRegisterFragment_to_OTPVerificationFragment)
            }
        }


    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLoginOrRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
        FirebaseAuth.getInstance().firebaseAuthSettings.forceRecaptchaFlowForTesting(true)

        initView()
        initClick()
    }

    private fun initView() = binding.apply {
        inRegister.tvTermCondition.movementMethod = LinkMovementMethod.getInstance()
        loginAndRegisterTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        isLogin = true
                        registerLayout.isVisible = false
                        loginLayout.isVisible = true

                    }
                    1 -> {
                        isLogin = false
                        registerLayout.isVisible = true
                        loginLayout.isVisible = false
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })

    }


    private fun initClick() = binding.apply {
        btnVerification.setOnClickListener {

            if (isLogin) {
                if (inLogin.loginEtMobileNumber.validPhone()) {
                    val code = inLogin.loginCountryCodePicker.selectedCountryCodeWithPlus
                    phone = inLogin.loginEtMobileNumber.text.toString()
                    checkUserExist(code , phone)

                }

            } else {
                if (inRegister.etMobileNumber.validPhone() && inRegister.etName.validName()) {
                    userName = inRegister.etName.text.toString()
                    countryCode = inRegister.countryCodePicker.selectedCountryCodeWithPlus
                    phone = inRegister.etMobileNumber.text.toString()
                    val mobileNumber = countryCode + phone
                    sendOtp(mobileNumber)

                }


            }
        }


    }

    private fun sendOtp(phone: String) {
        val option = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(phone)
            .setTimeout(60L, TimeUnit.MICROSECONDS)
            .setActivity(requireActivity())
            .setCallbacks(mCallback)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(option)
    }

    private fun checkUserExist(code: String, mobile: String) {
        db.collection(DatabaseConstant.USER_DB)
            .whereEqualTo("userPhone", mobile)
            .get()
            .addOnSuccessListener { user ->
                if (!user.isEmpty) {
                    sendOtp(code+mobile)
                } else {
                    Toast.makeText(
                        context,
                        "No user found ! please register first",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }.addOnFailureListener {
                Toast.makeText(context, "something went go wrong !", Toast.LENGTH_SHORT).show()
            }
    }


}