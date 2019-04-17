package com.ng.revoluttestapp.domain.entity

import com.ng.revoluttestapp.data.model.CurrencyRate
import com.ng.revoluttestapp.data.model.ExchangeData

class ExchangeMapper(private val mapper: Mapper<CurrencyRate, CurrencyEntity>) : Mapper<ExchangeData, ExchangeEntity> {
    override fun map(from: ExchangeData): ExchangeEntity {
        return ExchangeEntity(
            selectedCurrency = CurrencyEntity(
                currencyName = from.base,
                rate = CurrencyEntity.DEFAULT_RATE,
                isSelect = true
            ),
            currencies = from.rates.map { mapper.map(it) }
        )
    }
}