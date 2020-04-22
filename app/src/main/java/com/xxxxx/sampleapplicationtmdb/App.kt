package com.xxxxx.sampleapplicationtmdb

import android.app.Application
import com.xxxxx.sampleapplicationtmdb.di.AppComponent
import com.xxxxx.sampleapplicationtmdb.di.DaggerAppComponent
import com.xxxxx.sampleapplicationtmdb.ui.MainActivity

class App : Application(), Injectable {
    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent
            .builder()
            .build()
    }

    override fun inject(activity: MainActivity) {
        component.inject(activity)
    }
}

