package com.nide.pocketpass.ui.settings

import android.content.Context
import androidx.lifecycle.ViewModel
import com.nide.pocketpass.data.local.util.LocalDataStore
import com.nide.pocketpass.data.local.util.userDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(@ApplicationContext context: Context) : ViewModel() {

    val userName = context.userDataStore.data.map { pref ->
        pref[LocalDataStore.userName_Key] ?: ""
    }

    val userPhone = context.userDataStore.data.map { pref ->
        (pref[LocalDataStore.userCountryCode_Key] ?: "") + (pref[LocalDataStore.userPhone_key] ?: "")
    }


}