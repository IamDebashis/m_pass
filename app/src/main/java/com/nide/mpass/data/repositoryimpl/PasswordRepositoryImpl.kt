package com.nide.mpass.data.repositoryimpl

import com.nide.mpass.data.local.datasource.PasswordLocalDataSource
import com.nide.mpass.data.module.Password
import com.nide.mpass.domain.repository.PasswordRepository
import kotlinx.coroutines.flow.Flow

class PasswordRepositoryImpl(private val passwordLocalDataSource: PasswordLocalDataSource) : PasswordRepository{


    override fun getAllPasswords(): Flow<List<Password>> {
        return getPasswordFromDb()
    }

    override fun insertPassword(password: Password) {
        passwordLocalDataSource.insertPassword(password)
    }

    private fun getPasswordFromDb(): Flow<List<Password>> {
        return passwordLocalDataSource.getAllPassword()
    }



}