package com.ng.revoluttestapp.di

import com.google.gson.GsonBuilder
import com.ng.revoluttestapp.data.api.Api
import com.ng.revoluttestapp.data.api.ExchangeRateAdapter
import com.ng.revoluttestapp.data.model.CurrencyRate
import com.ng.revoluttestapp.data.model.ExchangeData
import com.ng.revoluttestapp.data.repo.NetworkStorageCurrency
import com.ng.revoluttestapp.domain.entity.*
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        val retrofitBuilder = Retrofit.Builder()

        val gson = GsonBuilder()
            .registerTypeAdapter(ExchangeData::class.java, ExchangeRateAdapter())
            .create()

        return retrofitBuilder.client(OkHttpClient.Builder().build())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(Api.BASE_URL)
            .build()
    }

    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit): Api {
        return retrofit.create(Api::class.java)
    }

    @Singleton
    @Provides
    fun provideCurrencyNetworkStorage(api: Api, mapper: Mapper<ExchangeData, ExchangeEntity>) =
        NetworkStorageCurrency(api, mapper)

    @Singleton
    @Provides
    fun provideExchangeMapper(mapper: Mapper<CurrencyRate, CurrencyEntity>): Mapper<ExchangeData, ExchangeEntity> =
        ExchangeMapper(mapper)

    @Singleton
    @Provides
    fun provideCurrencyMapper(): Mapper<CurrencyRate, CurrencyEntity> =
        CurrencyMapper()
}