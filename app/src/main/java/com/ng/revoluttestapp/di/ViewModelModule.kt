package com.ng.revoluttestapp.di

import com.ng.revoluttestapp.data.repo.CurrencyRepository
import com.ng.revoluttestapp.domain.usecase.AsyncTransformer
import com.ng.revoluttestapp.domain.usecase.GetExchangeRate
import com.ng.revoluttestapp.presentation.viewmodel.MainViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ViewModelModule {

    @Singleton
    @Provides
    fun provideMainViewModelFactory(getExchangeRate: GetExchangeRate) = MainViewModelFactory(getExchangeRate)

    @Singleton
    @Provides
    fun provideUseCase(currencyRepository: CurrencyRepository) = GetExchangeRate(AsyncTransformer(), currencyRepository)
}