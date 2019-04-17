package com.ng.revoluttestapp.domain.entity

import com.ng.revoluttestapp.presentation.adapter.BaseItem

data class CurrencyEntity(
    val currencyName: String,
    val rate: Double,
    val isSelect: Boolean
) : BaseItem {
    override val id = currencyName

    companion object {
        const val DEFAULT_RATE = 1.0
    }
}