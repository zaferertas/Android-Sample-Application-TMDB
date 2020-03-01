package com.xxxxx.sampleapplicationtmdb.data

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class Repository @Inject constructor(private val serviceApi: Api) {

    fun getPopularMovies(observer: DisposableSingleObserver<MoviePageResult>): Disposable {

        return serviceApi.getPopularMovies(1, API_KEY) //TODO:implement pager
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(observer)
    }

    fun getMovieDetails(
        movieId: Int,
        observer: DisposableSingleObserver<MovieDetails>
    ): Disposable {

        return serviceApi.getMovie(movieId, API_KEY)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(observer)
    }

}