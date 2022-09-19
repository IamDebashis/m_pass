package com.nide.mpass.domain.usecase

import com.nide.mpass.data.module.Password
import com.nide.mpass.domain.repository.PasswordRepository
import kotlinx.coroutines.flow.Flow

class SearchInPasswordUseCase(private val passwordRepository: PasswordRepository) {


    fun execute(query: String): Flow<List<Password>> {
        return passwordRepository.getPasswordBySearch(query)
    }

}