package com.nide.pocketpass.ui.start.login

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.nide.pocketpass.data.remote.util.DatabaseConstant
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class LoginViewModel : ViewModel() {


    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private var db: FirebaseFirestore = Firebase.firestore
    private val _timerText = MutableStateFlow(60)
    val timerText = _timerText.asStateFlow()


    fun startCountDown() = viewModelScope.launch {
        for (i in 60 downTo 0) {
            delay(1000)
            _timerText.emit(i)
        }
    }


    fun sendOtp(
        phone: String,
        mCallback: OnVerificationStateChangedCallbacks,
        activity: Activity
    ) {
        val option = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(phone)
            .setTimeout(60L, TimeUnit.MICROSECONDS)
            .setActivity(activity)
            .setCallbacks(mCallback)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(option)
    }

    fun checkUserExist(
        mobile: String,
        onSuccessCallback: (Boolean) -> Unit,
        onFailureCallback: (Exception) -> Unit
    ) {
        db.collection(DatabaseConstant.USER_DB)
            .whereEqualTo("userPhone", mobile)
            .get()
            .addOnSuccessListener { user ->
                onSuccessCallback(!user.isEmpty)

            }.addOnFailureListener {
                onFailureCallback(it)
            }
    }


}