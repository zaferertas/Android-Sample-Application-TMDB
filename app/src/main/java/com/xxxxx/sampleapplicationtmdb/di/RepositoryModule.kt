package com.xxxxx.sampleapplicationtmdb.di

import com.xxxxx.sampleapplicationtmdb.data.Repository
import com.xxxxx.sampleapplicationtmdb.data.RepositoryImpl
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(repositoryImpl: RepositoryImpl): Repository
}