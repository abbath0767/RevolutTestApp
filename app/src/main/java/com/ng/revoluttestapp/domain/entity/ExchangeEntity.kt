package com.ng.revoluttestapp.domain.entity

data class ExchangeEntity(
    val selectedCurrency: CurrencyEntity,
    val currencies: List<CurrencyEntity>
)