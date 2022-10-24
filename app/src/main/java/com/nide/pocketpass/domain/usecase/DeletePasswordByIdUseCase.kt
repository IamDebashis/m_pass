package com.nide.pocketpass.domain.usecase

import com.nide.pocketpass.domain.repository.PasswordRepository

class DeletePasswordByIdUseCase(private val repository: PasswordRepository) {

    fun execute(id: Int) = repository.deletePasswordById(id)

}