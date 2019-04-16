package com.ng.revoluttestapp.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.ng.revoluttestapp.domain.entity.CurrencyEntity
import com.ng.revoluttestapp.domain.entity.ExchangeEntity
import com.ng.revoluttestapp.domain.usecase.GetExchangeRate
import com.ng.revoluttestapp.util.TimberExtension.d
import com.ng.revoluttestapp.util.TimberExtension.e

class MainViewModel(private val getExchangeRate: GetExchangeRate) : BaseViewModel() {

    val viewState = MutableLiveData<MainViewState>()

    fun loadData() {
        viewState.value = MainViewState()

        addDisposable(
            getExchangeRate.observable()
                .subscribe(
                    { receiveData(it) },
                    { e { "error: $it" } }
                )
        )
    }

    private fun receiveData(exchange: ExchangeEntity) {
        viewState.value = viewState.value?.copy(exchangeEntity = exchange)
    }

    fun onCurrencyClick(currency: CurrencyEntity) {
        d {"on currency click: $currency"}
    }
}