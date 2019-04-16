package com.ng.revoluttestapp.domain.entity

import com.ng.revoluttestapp.data.repo.CurrencyRepository
import com.ng.revoluttestapp.data.repo.NetworkStorageCurrency
import io.reactivex.Observable

class CurrencyRepositoryImpl(private val networkStorage: NetworkStorageCurrency) : CurrencyRepository {

    override fun getExchangeRate(selectedCurrencyName: String?): Observable<ExchangeEntity> {
        return networkStorage.getExchangeRate(selectedCurrencyName)
    }
}