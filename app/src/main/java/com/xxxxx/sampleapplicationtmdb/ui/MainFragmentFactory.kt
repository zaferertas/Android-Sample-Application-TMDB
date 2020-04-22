package com.xxxxx.sampleapplicationtmdb.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.xxxxx.sampleapplicationtmdb.ui.moviedetails.MovieDetailsFragment
import com.xxxxx.sampleapplicationtmdb.ui.movielist.MovieListFragment
import javax.inject.Inject

class MainFragmentFactory @Inject constructor(
    private val mainViewModelFactory: MainViewModelFactory
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            MovieListFragment::class.java.canonicalName -> MovieListFragment(
                mainViewModelFactory
            )
            MovieDetailsFragment::class.java.canonicalName -> MovieDetailsFragment(
                mainViewModelFactory
            )
            else -> TODO("Missing fragment $className")
        }
    }
}