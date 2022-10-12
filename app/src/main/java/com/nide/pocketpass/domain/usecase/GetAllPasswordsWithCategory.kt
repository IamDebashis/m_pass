package com.nide.pocketpass.domain.usecase

import com.nide.pocketpass.domain.repository.PasswordRepository

class GetAllPasswordsWithCategory(private val repository: PasswordRepository) {

    fun execute() = repository.getAlPasswordsWithCategory()

}