package com.nide.pocketpass.data.local.dao

import androidx.room.*
import com.nide.pocketpass.data.module.Category
import kotlinx.coroutines.flow.Flow


@Dao
interface CategoryDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(vararg category: Category)

    @Delete
    suspend fun deleteCategory(category: Category)

    @Query("SELECT * FROM ${Category.CATEGORY_TABLE}")
     fun getAllCategory(): Flow<List<Category>>


}