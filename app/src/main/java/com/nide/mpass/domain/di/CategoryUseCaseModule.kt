package com.nide.mpass.domain.di

import com.nide.mpass.domain.repository.CategoryRepository
import com.nide.mpass.domain.usecase.GetAllCategoryUseCase
import com.nide.mpass.domain.usecase.SaveNewCategoryUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
class CategoryUseCaseModule {

    @Provides
    fun provideGetAllCategoryUseCase(repository: CategoryRepository): GetAllCategoryUseCase {
        return GetAllCategoryUseCase(repository)
    }

    @Provides
    fun provideSaveNewCategoryUseCase(repository: CategoryRepository): SaveNewCategoryUseCase {
        return SaveNewCategoryUseCase(repository)
    }



}