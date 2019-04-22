package com.ng.revoluttestapp

import com.ng.revoluttestapp.data.model.CurrencyRate
import com.ng.revoluttestapp.data.model.ExchangeData
import com.ng.revoluttestapp.domain.entity.CurrencyMapper
import com.ng.revoluttestapp.domain.entity.ExchangeMapper
import org.junit.Assert.assertEquals
import org.junit.Test

class MapperTest {

    private val currencyMapper = CurrencyMapper()
    private val exchangeMapper = ExchangeMapper(currencyMapper)
    private val name = "currencyName"
    private val nameOne = "currencyName_1"
    private val nameTwo = "currencyName_2"
    private val nameTree = "currencyName_3"
    private val rate = 13.0
    private val rateOne = 21.0
    private val rateTwo = 32.0
    private val rateTree = 43.3
    private val netCurrencyEntity = CurrencyRate(name, rate)
    private val netExchangeEntity = ExchangeData(
        base = name,
        date = "ignore",
        rates = listOf(
            CurrencyRate(nameOne, rateOne),
            CurrencyRate(nameTwo, rateTwo),
            CurrencyRate(nameTree, rateTree)
        )
    )

    @Test
    fun testCurrencyMappers() {
        val currencyEntity = currencyMapper.map(netCurrencyEntity)
        assertEquals(currencyEntity.currencyName, name)
        assertEquals(currencyEntity.rate, rate, 0.0)
        assertEquals(currencyEntity.isSelect, false)
    }

    @Test
    fun testExchangeMapper() {
        val exchangeEntity = exchangeMapper.map(netExchangeEntity)
        assertEquals(exchangeEntity.selectedCurrency.currencyName, name)
        assertEquals(exchangeEntity.selectedCurrency.rate, 1.0, 0.0)
        assertEquals(exchangeEntity.currencies[0].currencyName, nameOne)
        assertEquals(exchangeEntity.currencies[1].currencyName, nameTwo)
        assertEquals(exchangeEntity.currencies[2].currencyName, nameTree)
        assertEquals(exchangeEntity.currencies[0].rate, rateOne, 0.0)
        assertEquals(exchangeEntity.currencies[1].rate, rateTwo, 0.0)
        assertEquals(exchangeEntity.currencies[2].rate, rateTree, 0.0)
    }
}