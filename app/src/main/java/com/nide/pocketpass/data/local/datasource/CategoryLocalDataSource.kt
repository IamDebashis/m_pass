package com.nide.pocketpass.data.local.datasource

import com.nide.pocketpass.data.local.dao.CategoryDao
import com.nide.pocketpass.data.module.Category
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoryLocalDataSource(private val categoryDao: CategoryDao) {


    fun insertCategory(category: Category) = CoroutineScope(Dispatchers.IO).launch {
        categoryDao.insertCategory(category)
    }

    fun deleteCategory(category: Category) = CoroutineScope(Dispatchers.IO).launch {
        categoryDao.deleteCategory(category)
    }

    fun getCategories() = categoryDao.getAllCategory()


}