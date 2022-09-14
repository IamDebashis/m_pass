package com.nide.mpass.data.local.dao

import androidx.room.*
import com.nide.mpass.data.module.Category
import kotlinx.coroutines.flow.Flow


@Dao
interface CategoryDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(vararg category: Category)

    @Delete
    suspend fun deleteCategory(category: Category)

    @Query("SELECT * FROM ${Category.CATEGORY_TABLE_NAME}")
     fun getAllCategory(): Flow<List<Category>>


}