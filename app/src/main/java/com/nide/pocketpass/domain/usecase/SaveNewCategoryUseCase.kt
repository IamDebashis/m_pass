package com.nide.pocketpass.domain.usecase

import com.nide.pocketpass.data.module.Category
import com.nide.pocketpass.domain.repository.CategoryRepository

class SaveNewCategoryUseCase(private val categoryRepository: CategoryRepository) {

     fun execute(category: Category) {
        categoryRepository.insertCategory(category)
    }

}