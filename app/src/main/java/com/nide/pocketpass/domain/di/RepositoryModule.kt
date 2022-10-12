package com.nide.pocketpass.domain.di

import com.nide.pocketpass.data.local.datasource.CategoryLocalDataSource
import com.nide.pocketpass.data.local.datasource.PasswordLocalDataSource
import com.nide.pocketpass.data.repositoryimpl.AutofillRepositoryImlp
import com.nide.pocketpass.data.repositoryimpl.CategoryRepositoryImpl
import com.nide.pocketpass.data.repositoryimpl.PasswordRepositoryImpl
import com.nide.pocketpass.domain.repository.AutofillRepository
import com.nide.pocketpass.domain.repository.CategoryRepository
import com.nide.pocketpass.domain.repository.PasswordRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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

    @Singleton
    @Provides
    fun provideAutofillRepository(passwordDatasource : PasswordLocalDataSource) : AutofillRepository{
        return AutofillRepositoryImlp(passwordDatasource)
    }

}