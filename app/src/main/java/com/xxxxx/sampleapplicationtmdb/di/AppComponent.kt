package com.xxxxx.sampleapplicationtmdb.di

import com.xxxxx.sampleapplicationtmdb.ui.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(activity: MainActivity)
}
