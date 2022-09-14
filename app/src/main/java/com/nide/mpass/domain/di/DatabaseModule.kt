package com.nide.mpass.domain.di

import android.content.Context
import androidx.room.Room
import com.nide.mpass.data.local.PasswordDatabase
import com.nide.mpass.data.local.PasswordDatabase.Companion.DATABASE_NAME
import com.nide.mpass.data.local.PasswordDatabase.Companion.getInstance
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {


    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): PasswordDatabase {
        return getInstance(context)
    }

    @Singleton
    @Provides
    fun providePasswordDao(database: PasswordDatabase) = database.passwordDao()

    @Singleton
    @Provides
    fun provideCategoryDao(database: PasswordDatabase) = database.categoryDao()

}
