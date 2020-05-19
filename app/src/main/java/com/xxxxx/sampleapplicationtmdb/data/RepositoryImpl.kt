package com.xxxxx.sampleapplicationtmdb.data

import com.xxxxx.sampleapplicationtmdb.API_KEY
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val apiService: ApiService) : Repository {

    override fun getPopularMovies(observer: DisposableSingleObserver<MoviePageResult>): Disposable {

        return apiService.getPopularMovies(
            1,
            API_KEY
        ) //TODO:implement pager
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(observer)
    }

    override fun getMovieDetails(
        movieId: Int,
        observer: DisposableSingleObserver<MovieDetails>
    ): Disposable {

        return apiService.getMovie(
            movieId,
            API_KEY
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(observer)
    }

}