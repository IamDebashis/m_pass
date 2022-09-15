package com.nide.mpass.ui.viewpassword

import androidx.lifecycle.ViewModel
import com.nide.mpass.domain.usecase.GetPasswordByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PasswordDetailsViewModel @Inject constructor(
    private val getPasswordByIdUseCase: GetPasswordByIdUseCase
) : ViewModel() {

    fun getPasswordById(id: Int) = getPasswordByIdUseCase.execute(id)

}