package com.nide.pocketpass.domain.di

import com.nide.pocketpass.domain.repository.PasswordRepository
import com.nide.pocketpass.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
class PasswordUseCaseModule {

    @Provides
    fun provideGetAllPasswordUseCase(repository: PasswordRepository): GetAllPasswordUseCase {
        return GetAllPasswordUseCase(repository)
    }
    @Provides
    fun providesSavePasswordUseCase(repository: PasswordRepository): SaveNewPasswordUseCase {
        return SaveNewPasswordUseCase(repository)
    }

    @Provides
    fun provideGetPasswordById(repository: PasswordRepository): GetPasswordByIdUseCase {
        return GetPasswordByIdUseCase(repository)
    }

    @Provides
    fun provideGetPasswordByIdWithCategory(repository: PasswordRepository) : GetPasswordByIdWithCategory {
    return GetPasswordByIdWithCategory(repository)
    }

    @Provides
    fun provideSearchINPassword(repository: PasswordRepository) : SearchInPasswordUseCase{
        return SearchInPasswordUseCase(repository)
    }

    @Provides
    fun provideUpdatePassword(repository: PasswordRepository): UpdatePasswordUseCase{
        return UpdatePasswordUseCase(repository)
    }

    @Provides
    fun providesGetAllPasswordsWithCategory(repository: PasswordRepository) : GetAllPasswordsWithCategory{
        return GetAllPasswordsWithCategory(repository)
    }

}