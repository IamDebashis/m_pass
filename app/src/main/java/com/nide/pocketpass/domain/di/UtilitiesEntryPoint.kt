package com.nide.pocketpass.domain.di

import com.nide.pocketpass.domain.repository.AutofillRepository
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@EntryPoint
@InstallIn(SingletonComponent::class)
interface UtilitiesEntryPoint {
    val autofillRepository : AutofillRepository

}