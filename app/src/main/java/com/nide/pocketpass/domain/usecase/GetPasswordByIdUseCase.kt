package com.nide.pocketpass.domain.usecase

import com.nide.pocketpass.domain.repository.PasswordRepository

class GetPasswordByIdUseCase(private val passwordRepository: PasswordRepository) {

     fun execute(id: Int) = passwordRepository.getPasswordById(id)

}