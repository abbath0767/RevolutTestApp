package com.ng.revoluttestapp.data.repo

import com.ng.revoluttestapp.data.api.Api
import com.ng.revoluttestapp.data.model.ExchangeData
import com.ng.revoluttestapp.domain.entity.ExchangeEntity
import com.ng.revoluttestapp.domain.entity.Mapper
import com.ng.revoluttestapp.util.TimberExtension.d
import io.reactivex.Observable

class NetworkStorageCurrency(
    private val api: Api,
    private val mapper: Mapper<ExchangeData, ExchangeEntity>
) {

    fun getExchangeRate(selectedCurrency: String?): Observable<ExchangeEntity> {

        return if (selectedCurrency == null) {
            api.getExchangeRateDefault()
        } else {
            api.getExchangeRate(selectedCurrency)
        }
            .doOnNext {
                d { "on next: $it" }
            }.doOnError {
                d { "error: $it" }
            }
            .map { mapper.map(it) }
    }
}