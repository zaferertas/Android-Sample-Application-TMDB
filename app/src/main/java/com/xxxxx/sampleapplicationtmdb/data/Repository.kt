package com.xxxxx.sampleapplicationtmdb.data

import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver

interface Repository {

    fun getPopularMovies(observer: DisposableSingleObserver<MoviePageResult>): Disposable
    fun getMovieDetails(movieId: Int, observer: DisposableSingleObserver<MovieDetails>): Disposable
}