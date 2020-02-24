package com.xxxxx.sampleapplicationtmdb

import android.app.Application
import com.xxxxx.sampleapplicationtmdb.di.AppComponent
import com.xxxxx.sampleapplicationtmdb.di.DaggerAppComponent

class App : Application() {

    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()

        component = DaggerAppComponent
            .builder()
            .build()
    }
}

