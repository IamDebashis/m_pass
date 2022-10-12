package com.nide.pocketpass.domain.usecase

import com.nide.pocketpass.data.module.Password
import com.nide.pocketpass.domain.repository.PasswordRepository

class SaveNewPasswordUseCase(private val repository: PasswordRepository) {

    fun execute(password: Password) {
        repository.insertPassword(password)
    }

}