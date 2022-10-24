package com.nide.pocketpass.data.local.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson


val Context.userDataStore: DataStore<Preferences> by preferencesDataStore(name = LocalDataStore.localUserDetails)
object LocalDataStore {
    private val userLogin = "isUserLogin"
    private val userName = "nameOfTheUser"
    private val userEmail = "emailOfTheUser"
    private val userPhone = "phoneOfTheUser"
    private val countryCode = "countryCodeOfTheUser"

    val localUserDetails = "LocalUser"

    val isUserLogin_Key = booleanPreferencesKey(userLogin)
    val userName_Key = stringPreferencesKey(userName)
    val userEmail_Key = stringPreferencesKey(userEmail)
    val userPhone_key = stringPreferencesKey(userPhone)
    val userCountryCode_Key = stringPreferencesKey(countryCode)



}