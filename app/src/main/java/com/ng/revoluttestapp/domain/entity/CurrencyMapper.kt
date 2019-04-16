package com.ng.revoluttestapp.domain.entity

import com.ng.revoluttestapp.data.model.CurrencyRate

class CurrencyMapper : Mapper<CurrencyRate, CurrencyEntity> {

    override fun map(from: CurrencyRate): CurrencyEntity {
        return CurrencyEntity(
            currencyName = from.name,
            rate = from.rate,
            isSelect = false
        )
    }
}