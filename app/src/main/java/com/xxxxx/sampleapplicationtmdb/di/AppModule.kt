package com.xxxxx.sampleapplicationtmdb.di

import com.xxxxx.sampleapplicationtmdb.data.API_BASE_URL
import com.xxxxx.sampleapplicationtmdb.data.Api
import com.xxxxx.sampleapplicationtmdb.data.Repository
import com.xxxxx.sampleapplicationtmdb.ui.MainViewModelFactory
import dagger.Module
import dagger.Provides
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.reactivex.rxjava3.disposables.CompositeDisposable
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): Api {
        return retrofit.create(Api::class.java)
    }

    @Provides
    @Singleton
    fun provideCompositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }

    @Provides
    @Singleton
    fun provideRepository(api: Api): Repository {
        return Repository(api)
    }
}

@Module
class ViewModelModule {

    @Provides
    @Singleton
    fun provideMainViewModelFactory(
        repository: Repository,
        compositeDisposable: CompositeDisposable
    ): MainViewModelFactory {
        return MainViewModelFactory(
            repository, compositeDisposable
        )
    }
}