package com.xxxxx.sampleapplicationtmdb.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.xxxxx.sampleapplicationtmdb.data.Repository
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory @Inject constructor(
    private val repository: Repository,
    private val compositeDisposable: CompositeDisposable
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(
                repository, compositeDisposable
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}