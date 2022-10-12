package com.nide.pocketpass.domain.usecase

import com.nide.pocketpass.data.truple.PasswordTuple
import com.nide.pocketpass.domain.repository.PasswordRepository
import kotlinx.coroutines.flow.Flow

class SearchInPasswordUseCase(private val passwordRepository: PasswordRepository) {


    fun execute(query: String): Flow<List<PasswordTuple>> {
        return passwordRepository.getPasswordBySearch(query)
    }

}