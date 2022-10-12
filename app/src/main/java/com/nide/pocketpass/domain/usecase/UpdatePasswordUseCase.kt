package com.nide.pocketpass.domain.usecase

import com.nide.pocketpass.data.module.Password
import com.nide.pocketpass.domain.repository.PasswordRepository

class UpdatePasswordUseCase(private val passwordRepository: PasswordRepository) {

    fun execute(password : Password) = passwordRepository.updatePassword(password)
}