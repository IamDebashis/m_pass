package com.nide.pocketpass.data.repositoryimpl

import com.nide.pocketpass.data.local.datasource.PasswordLocalDataSource
import com.nide.pocketpass.data.module.Password
import com.nide.pocketpass.data.truple.PasswordTuple
import com.nide.pocketpass.domain.repository.PasswordRepository
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

    override fun getPasswordByIdWithCategory(id: Int): Flow<PasswordTuple> {
        return passwordLocalDataSource.getPasswordByIdWithCategory(id)
    }

    private fun getPasswordFromDb(): Flow<List<Password>> {
        return passwordLocalDataSource.getAllPassword()
    }

    override fun getPasswordBySearch(query: String): Flow<List<PasswordTuple>> {
        return passwordLocalDataSource.getPasswordBySearch(query)
    }

    override fun getAlPasswordsWithCategory(): Flow<List<PasswordTuple>> {
       return passwordLocalDataSource.getAllPasswordWithCategory()
    }
}