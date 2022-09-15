package com.nide.mpass.domain.usecase

import com.nide.mpass.domain.repository.PasswordRepository

class GetPasswordByIdUseCase(private val passwordRepository: PasswordRepository) {

     fun execute(id: Int) = passwordRepository.getPasswordById(id)

}