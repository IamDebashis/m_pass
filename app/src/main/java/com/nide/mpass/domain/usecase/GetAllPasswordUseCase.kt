package com.nide.mpass.domain.usecase

import com.nide.mpass.domain.repository.PasswordRepository

class GetAllPasswordUseCase(private val passwordRepository: PasswordRepository) {

    fun execute() = passwordRepository.getAllPasswords()
}