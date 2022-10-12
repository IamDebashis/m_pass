package com.nide.pocketpass.domain.usecase

import com.nide.pocketpass.domain.repository.CategoryRepository

class GetAllCategoryUseCase(private val categoryRepository: CategoryRepository) {

    fun execute() = categoryRepository.getAllCategories()


}