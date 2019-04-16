package com.ng.revoluttestapp.data.api

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.ng.revoluttestapp.data.model.CurrencyRate
import com.ng.revoluttestapp.data.model.ExchangeData
import com.ng.revoluttestapp.util.getObject
import com.ng.revoluttestapp.util.getString
import java.lang.reflect.Type

class ExchangeRateAdapter : JsonDeserializer<ExchangeData> {
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext?): ExchangeData {
        val base = json.getString("base")
        val date = json.getString("date")

        val ratesObject = json.getObject("rates")
        val keys = ratesObject.keySet()
        val rates = keys.map { key -> CurrencyRate(key, ratesObject[key].asDouble) }

        return ExchangeData(base, date, rates)
    }
}