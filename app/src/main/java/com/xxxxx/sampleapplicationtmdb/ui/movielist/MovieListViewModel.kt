package com.xxxxx.sampleapplicationtmdb.ui.movielist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xxxxx.sampleapplicationtmdb.OpenForTesting
import com.xxxxx.sampleapplicationtmdb.data.MovieItem
import com.xxxxx.sampleapplicationtmdb.data.MoviePageResult
import com.xxxxx.sampleapplicationtmdb.data.Repository
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver

@OpenForTesting
class MovieListViewModel(
    private val repository: Repository,
    private val compositeDisposable: CompositeDisposable
) : ViewModel() {

    private var _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private var _errorLoadingList = MutableLiveData<Boolean>(false)
    val errorLoadingList: LiveData<Boolean>
        get() = _errorLoadingList

    private var _movieItems = MutableLiveData<List<MovieItem>>()
    val movieItems: LiveData<List<MovieItem>>
        get() = _movieItems

    init {
        loadPopularMovies()
    }

    fun loadPopularMovies() {
        println("zfrD loadPopularMovies called")
        _isLoading.value = true

        compositeDisposable.add(
            repository.getPopularMovies(object : DisposableSingleObserver<MoviePageResult>() {

                override fun onSuccess(result: MoviePageResult) {
                    _isLoading.value = false
                    _errorLoadingList.value = false
                    _movieItems.value = result.movieResult
                }

                override fun onError(e: Throwable) {
                    _isLoading.value = false
                    _errorLoadingList.value = true
                }
            })
        )
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}


