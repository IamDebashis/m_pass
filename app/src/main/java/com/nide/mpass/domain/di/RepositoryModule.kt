package com.nide.mpass.domain.di

import com.nide.mpass.data.local.datasource.CategoryLocalDataSource
import com.nide.mpass.data.local.datasource.PasswordLocalDataSource
import com.nide.mpass.data.repositoryimpl.CategoryRepositoryImpl
import com.nide.mpass.data.repositoryimpl.PasswordRepositoryImpl
import com.nide.mpass.domain.repository.CategoryRepository
import com.nide.mpass.domain.repository.PasswordRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {


    @Singleton
    @Provides
    fun providePasswordRepository(localDataSource: PasswordLocalDataSource): PasswordRepository {
        return PasswordRepositoryImpl(localDataSource)
    }

    @Singleton
    @Provides
    fun provideCategoryRepository(localDataSource: CategoryLocalDataSource): CategoryRepository {
        return CategoryRepositoryImpl(localDataSource)
    }


}