package com.nide.pocketpass.ui.profile

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Database
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.nide.pocketpass.data.local.util.LocalDataStore
import com.nide.pocketpass.data.local.util.userDataStore
import com.nide.pocketpass.data.remote.util.DatabaseConstant
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(@ApplicationContext context: Context) : ViewModel() {
    private val TAG = javaClass.simpleName
    private val db = Firebase.firestore
    private val currentUser = FirebaseAuth.getInstance().currentUser

    val userName = context.userDataStore.data.map { pref ->
        pref[LocalDataStore.userName_Key] ?: ""
    }

    val userPhone = context.userDataStore.data.map { pref ->
        pref[LocalDataStore.userPhone_key] ?: ""
    }
    val userEmail = context.userDataStore.data.map { pref ->
        pref[LocalDataStore.userEmail_Key] ?: ""
    }

    val countryCode = context.userDataStore.data.map { pref ->
        pref[LocalDataStore.userCountryCode_Key] ?: ""
    }

    fun updateUser(context: Context, name: String, email: String) = viewModelScope.launch {
//        Log.i("Profile view Model", "updateUser: ${currentUser?.uid}")
        try {
            db.collection(DatabaseConstant.USER_DB).document(currentUser!!.uid)
                .update(
                    mapOf(
                        "userName" to name.ifEmpty { userName.first() },
                        "userEmail" to email.ifEmpty { userEmail.first() }
                    )
                )
                .addOnSuccessListener {
                    runBlocking {
                        updateProfile(context, name, email)
                    }
                }

        } catch (e: Exception) {
            Log.i(TAG, "updateUser: $e")
        }

    }

    private suspend fun updateProfile(context: Context, name: String, email: String) {

        context.userDataStore.edit { setting ->
            setting[LocalDataStore.userName_Key] = name.ifEmpty { userName.first() }
            setting[LocalDataStore.userEmail_Key] = email.ifEmpty { userEmail.first() }

        }

    }


}