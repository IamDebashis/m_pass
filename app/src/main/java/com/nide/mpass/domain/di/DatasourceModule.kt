package com.nide.mpass.domain.di

import com.nide.mpass.data.local.dao.CategoryDao
import com.nide.mpass.data.local.dao.PasswordDao
import com.nide.mpass.data.local.datasource.CategoryLocalDataSource
import com.nide.mpass.data.local.datasource.PasswordLocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
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