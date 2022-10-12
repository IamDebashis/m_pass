package com.nide.pocketpass.domain.di

import com.nide.pocketpass.domain.repository.CategoryRepository
import com.nide.pocketpass.domain.usecase.GetAllCategoryUseCase
import com.nide.pocketpass.domain.usecase.SaveNewCategoryUseCase
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