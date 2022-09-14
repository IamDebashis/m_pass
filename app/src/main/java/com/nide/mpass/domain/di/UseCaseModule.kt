package com.nide.mpass.domain.di

import com.nide.mpass.domain.repository.PasswordRepository
import com.nide.mpass.domain.usecase.GetAllPasswordUseCase
import com.nide.mpass.domain.usecase.SaveNewPasswordUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {

    @Provides
    fun provideGetAllPasswordUseCase(repository: PasswordRepository): GetAllPasswordUseCase {
        return GetAllPasswordUseCase(repository)
    }
    @Provides
    fun providesSavePasswordUseCase(repository: PasswordRepository): SaveNewPasswordUseCase {
        return SaveNewPasswordUseCase(repository)
    }


}