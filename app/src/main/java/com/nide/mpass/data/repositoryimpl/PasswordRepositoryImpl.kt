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

    override fun deletePassword(password: Password) {
        passwordLocalDataSource.deletePassword(password)
    }

    override fun updatePassword(password: Password) {
        passwordLocalDataSource.updatePassword(password)
    }

    override fun getPasswordById(id: Int): Flow<Password> {
        return passwordLocalDataSource.getPasswordById(id)
    }

    private fun getPasswordFromDb(): Flow<List<Password>> {
        return passwordLocalDataSource.getAllPassword()
    }



}