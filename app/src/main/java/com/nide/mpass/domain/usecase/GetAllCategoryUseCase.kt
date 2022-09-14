package com.nide.mpass.domain.usecase

import com.nide.mpass.domain.repository.CategoryRepository

class GetAllCategoryUseCase(private val categoryRepository: CategoryRepository) {

    fun execute() = categoryRepository.getAllCategories()


}