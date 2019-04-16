package com.ng.revoluttestapp.data.api

import com.ng.revoluttestapp.data.model.ExchangeData
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    companion object {
        const val BASE_URL = "https://revolut.duckdns.org/"
    }

    @GET("latest")
    fun getExchangeRate(@Query("base") baseCurrencyCode: String): Observable<ExchangeData>

    @GET("latest")
    fun getExchangeRateDefault(): Observable<ExchangeData>
}