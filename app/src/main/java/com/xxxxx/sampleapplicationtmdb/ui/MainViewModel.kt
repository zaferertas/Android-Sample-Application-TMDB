package com.xxxxx.sampleapplicationtmdb.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xxxxx.sampleapplicationtmdb.data.*
import io.reactivex.rxjava3.observers.DisposableSingleObserver

class MainViewModel(private val repository: Repository) : ViewModel() {

    private var _isLoading = MutableLiveData<Boolean>(false)
    private var _errorLoadingList = MutableLiveData<Boolean>(false)
    private var _errorLoadingDetails = MutableLiveData<Boolean>(false)

    private var _items = MutableLiveData<List<MovieItem>>()
    private var _selectedMovie = MutableLiveData<MovieDetails>()

    val items: LiveData<List<MovieItem>>
        get() = _items

    val isLoading : LiveData<Boolean>
        get() = _isLoading

    val errorLoadingList  : LiveData<Boolean>
        get() = _errorLoadingList

    val errorLoadingDetails  : LiveData<Boolean>
        get() = _errorLoadingDetails

    val selectedMovie: LiveData<MovieDetails>
        get() = _selectedMovie

    init {
        getPopularMovies()
    }

    private fun getPopularMovies() {
        _isLoading.value = true

        repository.getPopularMovies(object : DisposableSingleObserver<MoviePageResult>() {

            override fun onSuccess(result: MoviePageResult) {
                _isLoading.value = false
                _errorLoadingList.value = false
                _items.value = result.movieResult
            }

            override fun onError(e: Throwable) {
                _isLoading.value = false
                _errorLoadingList.value = true
            }
        })
    }

    private fun getMovieDetails(movieId: Int) {
        _isLoading.value = true

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
    }

    fun onItemSelected(movieId : Int) {
        getMovieDetails(movieId)
    }

    override fun onCleared() {
        super.onCleared()
        repository.onCleared()
    }
}


