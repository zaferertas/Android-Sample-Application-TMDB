package com.xxxxx.sampleapplicationtmdb.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xxxxx.sampleapplicationtmdb.data.MovieDetails
import com.xxxxx.sampleapplicationtmdb.data.MovieItem
import com.xxxxx.sampleapplicationtmdb.data.MoviePageResult
import com.xxxxx.sampleapplicationtmdb.data.Repository
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver

class MainViewModel(
    private val repository: Repository,
    private val compositeDisposable: CompositeDisposable
) : ViewModel() {

    private var _isLoading = MutableLiveData<Boolean>(false)
    private var _errorLoadingList = MutableLiveData<Boolean>(false)
    private var _errorLoadingDetails = MutableLiveData<Boolean>(false)

    private var _movieItems = MutableLiveData<List<MovieItem>>()
    private var _selectedMovie = MutableLiveData<MovieDetails>()

    val isLoading: LiveData<Boolean>
        get() = _isLoading

    val errorLoadingList: LiveData<Boolean>
        get() = _errorLoadingList

    val errorLoadingDetails: LiveData<Boolean>
        get() = _errorLoadingDetails

    val movieItems: LiveData<List<MovieItem>>
        get() = _movieItems

    val selectedMovie: LiveData<MovieDetails>
        get() = _selectedMovie

    init {
        loadPopularMovies()
    }

    fun loadPopularMovies() {

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

    fun onItemSelected(movieId: Int) {
        loadMovieDetails(movieId)
    }

    fun loadMovieDetails(movieId: Int) {

        _isLoading.value = true

        compositeDisposable.add(
            repository.getMovieDetails(movieId, object : DisposableSingleObserver<MovieDetails>() {

                override fun onSuccess(result: MovieDetails) {
                    _isLoading.value = false
                    _errorLoadingDetails.value = false
                    _selectedMovie.value = result
                }

                override fun onError(e: Throwable) {
                    _isLoading.value = false
                    _errorLoadingDetails.value = true
                }
            })
        )

/*        compositeDisposable.add(
            repository.getMovieDetails(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<MovieDetails>() {

                    override fun onSuccess(result: MovieDetails) {
                        _isLoading.value = false
                        _errorLoadingDetails.value = false
                        _selectedMovie.value = result
                    }

                    override fun onError(e: Throwable) {
                        _isLoading.value = false
                        _errorLoadingDetails.value = true
                    }
                })
        )*/
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}


