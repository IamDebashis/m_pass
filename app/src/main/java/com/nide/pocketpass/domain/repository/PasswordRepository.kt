package com.nide.pocketpass.domain.repository

import com.nide.pocketpass.data.module.Password
import com.nide.pocketpass.data.truple.PasswordTuple
import kotlinx.coroutines.flow.Flow

interface PasswordRepository {

    fun getAllPasswords(): Flow<List<Password>>
    fun insertPassword(password: Password)
    fun deletePassword(password: Password)
    fun deletePasswordById(id:Int)
    fun updatePassword(password: Password)
    fun getPasswordById(id: Int): Flow<Password>
    fun getPasswordByIdWithCategory(id: Int) : Flow<PasswordTuple>
    fun getPasswordBySearch(query: String):Flow<List<PasswordTuple>>
    fun getAlPasswordsWithCategory():Flow<List<PasswordTuple>>


}