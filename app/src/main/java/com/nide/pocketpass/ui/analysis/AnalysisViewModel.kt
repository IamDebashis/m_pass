package com.nide.pocketpass.ui.analysis

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nide.pocketpass.domain.usecase.GetAllPasswordUseCase
import com.nide.pocketpass.util.password_util.PasswordUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AnalysisViewModel @Inject constructor(
    private val getAllPasswordUseCase: GetAllPasswordUseCase
) : ViewModel() {

    val passwords = getAllPasswordUseCase.execute()

    val analysis = passwords.map { passwordList ->
        val util = PasswordUtil()
        util.checkSecurePasswords(passwordList)
        util
    }.flowOn(Dispatchers.IO)
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            PasswordUtil()
        )


}