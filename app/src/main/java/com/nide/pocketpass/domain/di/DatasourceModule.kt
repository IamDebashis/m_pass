package com.nide.pocketpass.domain.di

import com.nide.pocketpass.data.local.dao.CategoryDao
import com.nide.pocketpass.data.local.dao.PasswordDao
import com.nide.pocketpass.data.local.datasource.CategoryLocalDataSource
import com.nide.pocketpass.data.local.datasource.PasswordLocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatasourceModule {

    @Singleton
    @Provides
    fun providePasswordLocalDataSource(passwordDao: PasswordDao): PasswordLocalDataSource {
        return PasswordLocalDataSource(passwordDao)
    }
    @Singleton
    @Provides
    fun provideCategoryLocalDataSource(categoryDao: CategoryDao): CategoryLocalDataSource {
        return CategoryLocalDataSource(categoryDao)
    }

}