package com.nide.pocketpass.domain.usecase

import com.nide.pocketpass.domain.repository.PasswordRepository

class GetAllPasswordUseCase(private val passwordRepository: PasswordRepository) {

    fun execute() = passwordRepository.getAllPasswords()
}