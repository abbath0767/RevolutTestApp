package com.ng.revoluttestapp.domain.usecase

import com.ng.revoluttestapp.data.repo.CurrencyRepository
import com.ng.revoluttestapp.domain.entity.ExchangeEntity
import io.reactivex.Observable

class GetExchangeRate(
    transformer: Transformer<ExchangeEntity>,
    private val currencyRepository: CurrencyRepository
) : UseCaseRepeatable<ExchangeEntity>(transformer) {

    companion object {
        const val KEY_SELECTED_CURRENCY = "KEY_SELECTED_CURRENCY"
    }

    override fun createObservable(data: Map<String, Any>?): Observable<ExchangeEntity> {
        return if (data.isNullOrEmpty()) {
            currencyRepository.getExchangeRate()
        } else if (data.containsKey(KEY_SELECTED_CURRENCY) && data[KEY_SELECTED_CURRENCY] is String) {
            val currencyName = data[KEY_SELECTED_CURRENCY] as String
            currencyRepository.getExchangeRate(currencyName)
        } else {
            Observable.error(Throwable("Unsupported keys: ${data.keys}"))
        }
    }
}