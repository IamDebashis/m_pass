package com.nide.pocketpass.domain.usecase

import com.nide.pocketpass.domain.repository.PasswordRepository

class GetPasswordByIdWithCategory(private val passwordRepository: PasswordRepository) {

    fun execute(id:Int) = passwordRepository.getPasswordByIdWithCategory(id)

}