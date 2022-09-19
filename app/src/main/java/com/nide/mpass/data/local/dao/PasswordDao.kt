package com.nide.mpass.data.local.dao

import androidx.room.*
import com.nide.mpass.data.truple.PasswordTuple
import com.nide.mpass.data.module.Category.Companion.CATEGORY_TABLE
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

    @Query("DELETE FROM $PASSWORD_TABLE")
    suspend fun deleteAllPassword()

    @Query("SELECT * FROM $PASSWORD_TABLE")
    fun getAllPassword(): Flow<List<Password>>

    @Query("SELECT * FROM $PASSWORD_TABLE WHERE id = :id")
    fun getPasswordById(id: Int): Flow<Password>

    // get password by category
    @Query("SELECT * FROM $PASSWORD_TABLE WHERE category_id = :category")
    fun getPasswordByCategory(category: Int): Flow<List<Password>>

    // get password with category
    @Query("SELECT ${PASSWORD_TABLE}.* , ${CATEGORY_TABLE}.category_name FROM $PASSWORD_TABLE , $CATEGORY_TABLE WHERE $PASSWORD_TABLE.id = :id AND $CATEGORY_TABLE.id = $PASSWORD_TABLE.category_id")
    fun getPasswordWithCategory(id: Int): Flow<PasswordTuple>

    // search in password
    @Query("SELECT * FROM $PASSWORD_TABLE WHERE name LIKE :query OR user_id LIKE :query OR url LIKE :query")
    fun getPasswordBySearch(query: String):Flow<List<Password>>

}