package com.ng.revoluttestapp

import android.app.Application
import com.ng.revoluttestapp.di.AppComponent
import com.ng.revoluttestapp.di.DaggerAppComponent
import com.ng.revoluttestapp.di.NetModule
import timber.log.Timber
import timber.log.Timber.plant

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        initTimber()
        initDiGraph()
    }

    private fun initTimber() {
        plant(Timber.DebugTree())
    }

    private fun initDiGraph() {
        appComponent = DaggerAppComponent.builder()
            .netModule(NetModule())
            .build()
    }
}