package com.nide.mpass.domain.repository

import com.nide.mpass.data.module.Password
import kotlinx.coroutines.flow.Flow

interface PasswordRepository {

    fun getAllPasswords(): Flow<List<Password>>
    fun insertPassword(password: Password)
    fun deletePassword(password: Password)
    fun updatePassword(password: Password)
    fun getPasswordById(id: Int): Flow<Password>

}