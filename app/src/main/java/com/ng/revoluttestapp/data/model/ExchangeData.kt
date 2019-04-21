package com.ng.revoluttestapp.data.model

data class ExchangeData(
    val base: String,
    val date: String,
    val rates: List<CurrencyRate>
)

data class CurrencyRate(
    val name: String,
    val rate: Double
)