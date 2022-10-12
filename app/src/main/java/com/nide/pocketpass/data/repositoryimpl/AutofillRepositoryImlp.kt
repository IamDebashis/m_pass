package com.nide.pocketpass.data.repositoryimpl

import com.nide.pocketpass.data.local.datasource.PasswordLocalDataSource
import com.nide.pocketpass.data.module.Password
import com.nide.pocketpass.domain.repository.AutofillRepository

class AutofillRepositoryImlp(private val passwordDataSource : PasswordLocalDataSource) : AutofillRepository {

    override fun getAutofillPassword(name :String): List<Password> {
        return passwordDataSource.getAutofillPassword(name)
    }
}