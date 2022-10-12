package com.nide.pocketpass.ui.viewpassword

import androidx.lifecycle.ViewModel
import com.nide.pocketpass.domain.usecase.GetPasswordByIdUseCase
import com.nide.pocketpass.domain.usecase.GetPasswordByIdWithCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PasswordDetailsViewModel @Inject constructor(
    private val getPasswordByIdUseCase: GetPasswordByIdUseCase,
    private val getPasswordByIdWithCategory: GetPasswordByIdWithCategory
) : ViewModel() {

    fun getPasswordById(id: Int) = getPasswordByIdWithCategory.execute(id)

}