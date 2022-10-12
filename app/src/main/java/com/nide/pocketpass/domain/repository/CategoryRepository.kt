package com.nide.pocketpass.domain.repository

import com.nide.pocketpass.data.module.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    fun getAllCategories(): Flow<List<Category>>
    fun insertCategory(category: Category)

}