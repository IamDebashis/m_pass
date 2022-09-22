package com.nide.mpass.domain.usecase

import com.nide.mpass.data.module.Password
import com.nide.mpass.domain.repository.PasswordRepository

class UpdatePasswordUseCase(private val passwordRepository: PasswordRepository) {

    fun execute(password : Password) = passwordRepository.updatePassword(password)
}