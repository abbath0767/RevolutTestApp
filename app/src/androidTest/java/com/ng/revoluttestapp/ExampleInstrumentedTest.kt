package com.ng.revoluttestapp

import android.support.test.runner.AndroidJUnit4
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer
import com.ng.revoluttestapp.data.repo.CurrencyRepository
import com.ng.revoluttestapp.domain.entity.CurrencyEntity
import com.ng.revoluttestapp.domain.entity.ExchangeEntity
import com.ng.revoluttestapp.domain.usecase.AsyncTransformer
import com.ng.revoluttestapp.domain.usecase.GetExchangeRate
import com.ng.revoluttestapp.presentation.viewmodel.MainViewModel
import io.reactivex.Observable
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val lifecycleOwner = object : LifecycleOwner {
        val lifecycleRegistry = LifecycleRegistry(this)

        override fun getLifecycle(): Lifecycle {
            return lifecycleRegistry
        }
    }

    private val selectedName = "EUR"
    private val nameFirst = "USD"
    private val nameSecond = "RUR"
    private val rateFirst = 1.2
    private val rateSecond = 22.22
    private val deltaTest = 0.0
    private val rateTestMultiplier = 2.0

    private val currencyRepository = object : CurrencyRepository {
        override fun getExchangeRate(selectedCurrencyName: String?): Observable<ExchangeEntity> {
            return Observable.just(
                ExchangeEntity(
                    selectedCurrency = CurrencyEntity(selectedName, 1.0, true),
                    currencies = listOf(
                        CurrencyEntity(nameFirst, rateFirst, false),
                        CurrencyEntity(nameSecond, rateSecond, false)
                    )
                )
            )
        }
    }
    private val getExchangeRate = GetExchangeRate(AsyncTransformer(), currencyRepository)

    @Test
    fun smokeViewModelTest() {
        val getExchangeRate = GetExchangeRate(AsyncTransformer(), currencyRepository)
        val viewModel = MainViewModel(getExchangeRate)

        viewModel.viewState.observe(lifecycleOwner, Observer {
            it.exchangeEntity?.let { exchange ->
                assertEquals(exchange.selectedCurrency.currencyName, selectedName)
                assertEquals(exchange.currencies[0].currencyName, nameFirst)
                assertEquals(exchange.currencies[1].currencyName, nameSecond)
                assertEquals(exchange.currencies[0].rate, rateFirst, deltaTest)
                assertEquals(exchange.currencies[1].rate, rateSecond, deltaTest)
            }
        })
        viewModel.loadData()

        lifecycleOwner.lifecycleRegistry.markState(Lifecycle.State.RESUMED)
    }

    @Test
    fun testChangeRate() {
        val viewModel = MainViewModel(getExchangeRate)

        viewModel.viewState.observe(lifecycleOwner, Observer {
            it.exchangeEntity?.let { exchange ->
                assertEquals(exchange.selectedCurrency.currencyName, selectedName)
                assertEquals(exchange.selectedCurrency.rate * rateTestMultiplier, 1.0 * rateTestMultiplier, deltaTest)
                assertEquals(exchange.currencies[0].currencyName, nameFirst)
                assertEquals(exchange.currencies[1].currencyName, nameSecond)
                assertEquals(
                    exchange.currencies[0].rate * rateTestMultiplier,
                    rateFirst * rateTestMultiplier,
                    deltaTest
                )
                assertEquals(
                    exchange.currencies[1].rate * rateTestMultiplier,
                    rateSecond * rateTestMultiplier,
                    deltaTest
                )
            }
        })
        viewModel.loadData()
        viewModel.onRateChange(rateTestMultiplier)
        lifecycleOwner.lifecycleRegistry.markState(Lifecycle.State.RESUMED)

    }
}
