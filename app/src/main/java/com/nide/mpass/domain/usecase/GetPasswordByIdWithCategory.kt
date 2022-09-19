package com.nide.mpass.domain.usecase

import com.nide.mpass.domain.repository.PasswordRepository

class GetPasswordByIdWithCategory(private val passwordRepository: PasswordRepository) {

    fun execute(id:Int) = passwordRepository.getPasswordByIdWithCategory(id)

}