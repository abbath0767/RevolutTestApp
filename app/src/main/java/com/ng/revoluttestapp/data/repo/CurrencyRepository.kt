package com.ng.revoluttestapp.data.repo

import com.ng.revoluttestapp.domain.entity.ExchangeEntity
import io.reactivex.Observable

interface CurrencyRepository {
    fun getExchangeRate(selectedCurrencyName: String? = null): Observable<ExchangeEntity>
}