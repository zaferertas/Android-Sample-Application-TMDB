package com.xxxxx.sampleapplicationtmdb.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.xxxxx.sampleapplicationtmdb.data.Repository
import com.xxxxx.sampleapplicationtmdb.ui.moviedetails.MovieDetailsViewModel
import com.xxxxx.sampleapplicationtmdb.ui.movielist.MovieListViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory @Inject constructor(
    private val repository: Repository,
    private val compositeDisposable: CompositeDisposable
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            (modelClass.isAssignableFrom(MovieListViewModel::class.java)) -> {
                MovieListViewModel(
                    repository,
                    compositeDisposable
                ) as T
            }
            (modelClass.isAssignableFrom(MovieDetailsViewModel::class.java)) -> {
                MovieDetailsViewModel(
                    repository,
                    compositeDisposable
                ) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }

    }
}

