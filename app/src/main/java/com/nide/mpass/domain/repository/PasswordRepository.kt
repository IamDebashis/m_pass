package com.nide.mpass.domain.repository

import com.nide.mpass.data.module.Password
import kotlinx.coroutines.flow.Flow

interface PasswordRepository {

    fun getAllPasswords(): Flow<List<Password>>
    fun insertPassword(password: Password)

}