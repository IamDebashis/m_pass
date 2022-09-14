package com.nide.mpass.data.local.dao

import androidx.room.*
import com.nide.mpass.data.module.Password
import com.nide.mpass.data.module.Password.Companion.PASSWORD_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface PasswordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPassword(vararg password: Password)

    @Update
    suspend fun updatePassword(vararg password: Password)

    @Delete
    suspend fun deletePassword(vararg password: Password)

   /* @Query("DELETE * FROM $PASSWORD_TABLE")
    suspend fun deleteAllPassword()*/

    @Query("SELECT * FROM $PASSWORD_TABLE")
    fun getAllPassword(): Flow<List<Password>>

    @Query("SELECT * FROM $PASSWORD_TABLE WHERE id = :id")
    fun getPasswordById(id: Int): Password

    // get password by category
    @Query("SELECT * FROM $PASSWORD_TABLE WHERE category_id = :category")
    fun getPasswordByCategory(category: Int): Flow<List<Password>>


}