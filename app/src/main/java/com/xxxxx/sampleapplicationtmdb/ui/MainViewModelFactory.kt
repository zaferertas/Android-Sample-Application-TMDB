package com.xxxxx.sampleapplicationtmdb.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.xxxxx.sampleapplicationtmdb.data.Repository
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory @Inject constructor(private val repository: Repository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(
                repository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}