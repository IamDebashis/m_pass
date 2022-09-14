package com.nide.mpass.domain.repository

import com.nide.mpass.data.module.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    fun getAllCategories(): Flow<List<Category>>
    fun insertCategory(category: Category)

}