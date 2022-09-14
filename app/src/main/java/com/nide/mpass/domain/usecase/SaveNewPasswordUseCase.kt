package com.nide.mpass.domain.usecase

import com.nide.mpass.data.module.Password
import com.nide.mpass.domain.repository.PasswordRepository

class SaveNewPasswordUseCase(private val repository: PasswordRepository) {

    fun execute(password: Password) {
        repository.insertPassword(password)
    }

}