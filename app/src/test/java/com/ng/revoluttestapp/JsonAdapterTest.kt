package com.ng.revoluttestapp

import com.google.gson.GsonBuilder
import com.ng.revoluttestapp.data.api.ExchangeRateAdapter
import com.ng.revoluttestapp.data.model.ExchangeData
import org.junit.Assert.assertEquals
import org.junit.Test

class JsonAdapterTest {

    private val base = "EUR"
    private val date = "date"
    private val nameFirst = "AUD"
    private val nameSecond = "RUR"
    private val nameThird = "USD"
    private val rateFirst = "0.01"
    private val rateSecond = "22.33"
    private val rateThird = "32.0119"
    private val jsonString =
        "{\"base\":\"$base\"," +
                "\"date\":\"$date\"," +
                "\"rates\":" +
                "{\"$nameFirst\":$rateFirst," +
                "\"$nameSecond\":$rateSecond," +
                "\"$nameThird\":$rateThird" +
                "}" +
                "}"

    private val testDelta = 0.0

    @Test
    fun testJsonAdapter() {
        val gson = GsonBuilder()
            .registerTypeAdapter(ExchangeData::class.java, ExchangeRateAdapter())
            .create()

        val parseData = gson.fromJson(jsonString, ExchangeData::class.java)
        assertEquals(parseData.base, base)
        assertEquals(parseData.date, date)
        assertEquals(parseData.rates[0].name, nameFirst)
        assertEquals(parseData.rates[1].name, nameSecond)
        assertEquals(parseData.rates[2].name, nameThird)
        assertEquals(parseData.rates[0].rate, rateFirst.toDouble(), testDelta)
        assertEquals(parseData.rates[1].rate, rateSecond.toDouble(), testDelta)
        assertEquals(parseData.rates[2].rate, rateThird.toDouble(), testDelta)
    }
}