package com.nide.mpass.ui.viewpassword

import androidx.lifecycle.ViewModel
import com.nide.mpass.domain.usecase.GetPasswordByIdUseCase
import com.nide.mpass.domain.usecase.GetPasswordByIdWithCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PasswordDetailsViewModel @Inject constructor(
    private val getPasswordByIdUseCase: GetPasswordByIdUseCase,
    private val getPasswordByIdWithCategory: GetPasswordByIdWithCategory
) : ViewModel() {

    fun getPasswordById(id: Int) = getPasswordByIdWithCategory.execute(id)

}