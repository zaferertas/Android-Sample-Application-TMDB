package com.xxxxx.sampleapplicationtmdb.ui.moviedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xxxxx.sampleapplicationtmdb.OpenForTesting
import com.xxxxx.sampleapplicationtmdb.data.MovieDetails
import com.xxxxx.sampleapplicationtmdb.data.Repository
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver

@OpenForTesting
class MovieDetailsViewModel(
    private val repository: Repository,
    private val compositeDisposable: CompositeDisposable
) : ViewModel() {

    private var _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private var _errorLoadingDetails = MutableLiveData<Boolean>(false)
    val errorLoadingDetails: LiveData<Boolean>
        get() = _errorLoadingDetails

    private var _selectedMovie = MutableLiveData<MovieDetails>()
    val selectedMovie: LiveData<MovieDetails>
        get() = _selectedMovie

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
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}


