package com.ng.revoluttestapp.di

import com.ng.revoluttestapp.data.repo.CurrencyRepository
import com.ng.revoluttestapp.data.repo.NetworkStorageCurrency
import com.ng.revoluttestapp.domain.entity.CurrencyRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Singleton
    @Provides
    fun provideCurrencyRepository(networkStorage: NetworkStorageCurrency): CurrencyRepository =
        CurrencyRepositoryImpl(networkStorage)
}