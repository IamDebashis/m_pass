package com.nide.mpass.domain.usecase

import com.nide.mpass.data.module.Category
import com.nide.mpass.domain.repository.CategoryRepository

class SaveNewCategoryUseCase(private val categoryRepository: CategoryRepository) {

     fun execute(category: Category) {
        categoryRepository.insertCategory(category)
    }

}