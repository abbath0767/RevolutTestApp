package com.ng.revoluttestapp.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.ng.revoluttestapp.domain.entity.CurrencyEntity
import com.ng.revoluttestapp.domain.entity.ExchangeEntity
import com.ng.revoluttestapp.domain.usecase.GetExchangeRate
import com.ng.revoluttestapp.util.TimberExtension.e

class MainViewModel(private val getExchangeRate: GetExchangeRate) : BaseViewModel() {

    val viewState = MutableLiveData<MainViewState>()

    private var originalCurrencies = emptyList<CurrencyEntity>()

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
        originalCurrencies = exchange.currencies

        viewState.value?.exchangeEntity?.let { currentExchange ->
            val currentRate: Double
            val correctedExchange =
                if (exchange.selectedCurrency.currencyName == currentExchange.selectedCurrency.currencyName) {

                    currentRate = currentExchange.selectedCurrency.rate

                    currentExchange.copy(
                        selectedCurrency = exchange.selectedCurrency.copy(rate = currentRate),
                        currencies = exchange.currencies.map { it.copy(rate = it.rate * currentRate) }
                    )
                } else {

                    currentRate = currentExchange.currencies
                        .first { it.currencyName == exchange.selectedCurrency.currencyName }.rate

                    currentExchange.copy(
                        selectedCurrency = exchange.selectedCurrency.copy(rate = currentRate),
                        currencies = exchange.currencies.map { it.copy(rate = it.rate * currentRate) }
                    )
                }
            viewState.value = viewState.value?.copy(exchangeEntity = correctedExchange)
        } ?: run {
            viewState.value = viewState.value?.copy(exchangeEntity = exchange)
        }
    }

    fun onCurrencyClick(currency: CurrencyEntity) {
        onCleared()
        addDisposable(
            getExchangeRate.observable(mapOf(GetExchangeRate.KEY_SELECTED_CURRENCY to currency.currencyName))
                .subscribe(
                    { receiveData(it) },
                    { e { "error: $it" } }
                )
        )
    }

    fun onRateChange(newRate: Double) {
        viewState.value?.let { state ->
            if (state.exchangeEntity == null)
                return

            val exchange = state.exchangeEntity.copy(
                selectedCurrency = state.exchangeEntity.selectedCurrency.copy(rate = newRate),
                currencies = originalCurrencies.map { it.copy(rate = it.rate * newRate) }
            )

            viewState.value = viewState.value?.copy(
                exchangeEntity = exchange
            )
        }
    }
}