package com.nide.mpass.domain.repository

import android.database.sqlite.SQLiteQuery
import com.nide.mpass.data.module.Password
import com.nide.mpass.data.truple.PasswordTuple
import kotlinx.coroutines.flow.Flow

interface PasswordRepository {

    fun getAllPasswords(): Flow<List<Password>>
    fun insertPassword(password: Password)
    fun deletePassword(password: Password)
    fun updatePassword(password: Password)
    fun getPasswordById(id: Int): Flow<Password>
    fun getPasswordByIdWithCategory(id: Int) : Flow<PasswordTuple>
    fun getPasswordBySearch(query: String):Flow<List<Password>>


}