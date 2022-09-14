package com.nide.mpass.data.repositoryimpl

import com.nide.mpass.data.local.datasource.CategoryLocalDataSource
import com.nide.mpass.data.module.Category
import com.nide.mpass.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow

class CategoryRepositoryImpl(private val categoryLocalDataSource: CategoryLocalDataSource) :
    CategoryRepository {
    override fun getAllCategories(): Flow<List<Category>> {
        return categoryLocalDataSource.getCategories()
    }

    override fun insertCategory(category: Category) {
        categoryLocalDataSource.insertCategory(category)
    }


}