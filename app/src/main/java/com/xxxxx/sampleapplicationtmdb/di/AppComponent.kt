package com.xxxxx.sampleapplicationtmdb.di

import com.xxxxx.sampleapplicationtmdb.ui.DetailsFragment
import com.xxxxx.sampleapplicationtmdb.ui.MainFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    ViewModelModule::class
])
interface AppComponent {

    fun inject(fragment: MainFragment)
    fun inject(fragment: DetailsFragment)
}
