package com.xxxxx.sampleapplicationtmdb.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.xxxxx.sampleapplicationtmdb.data.MovieItem
import com.xxxxx.sampleapplicationtmdb.data.MoviePageResult
import com.xxxxx.sampleapplicationtmdb.data.Repository
import com.xxxxx.sampleapplicationtmdb.ui.movielist.MovieListViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MovieListViewModelTest {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    private val repository: Repository = mock()

    private val observerIsLoading = mock<Observer<Boolean>>()
    private val observerMovieItems = mock<Observer<List<MovieItem>>>()
    private val observerErrorLoadingList = mock<Observer<Boolean>>()

    private lateinit var movieListViewModel: MovieListViewModel
    private val compositeDisposable: CompositeDisposable = mock()

    @Before
    fun setUp() {
        movieListViewModel =
            MovieListViewModel(
                repository,
                compositeDisposable
            )
    }

    @Test
    fun `isLoading shows correct state before and after getPopularMovies executes with success`() {

        movieListViewModel.isLoading.observeForever(observerIsLoading)

        Assert.assertEquals(true, movieListViewModel.isLoading.value)

        argumentCaptor<DisposableSingleObserver<MoviePageResult>>().apply {
            verify(repository).getPopularMovies(capture())
            firstValue.onSuccess(getDummyMoviePageResultObject())
            Assert.assertEquals(false, movieListViewModel.isLoading.value)
        }
    }


    @Test
    fun `movieItems gets populated when loadPopularMovies executes successfully`() {

        movieListViewModel.movieItems.observeForever(observerMovieItems)

        argumentCaptor<DisposableSingleObserver<MoviePageResult>>().apply {
            verify(repository).getPopularMovies(capture())
            firstValue.onSuccess(getDummyMoviePageResultObject())
            Assert.assertEquals(3, movieListViewModel.movieItems.value?.size)
        }
    }

    @Test
    fun `errorLoadingList shows true when loadPopularMovies executes with error`() {

        movieListViewModel.errorLoadingList.observeForever(observerErrorLoadingList)

        argumentCaptor<DisposableSingleObserver<MoviePageResult>>().apply {
            verify(repository).getPopularMovies(capture())
            firstValue.onError(Exception())
            Assert.assertEquals(true, movieListViewModel.errorLoadingList.value)
        }
    }

    @After
    fun tearDown() {
        movieListViewModel.movieItems.removeObserver(observerMovieItems)
        movieListViewModel.isLoading.removeObserver(observerIsLoading)
        movieListViewModel.errorLoadingList.removeObserver(observerErrorLoadingList)
    }

    private fun getDummyMoviePageResultObject(): MoviePageResult {

        return MoviePageResult(
            page = 1,
            totalResults = 3,
            totalPages = 1,
            movieResult = arrayListOf(
                MovieItem(0, "", 1.0F, "", ""),
                MovieItem(1, "", 1.0F, "", ""),
                MovieItem(2, "", 1.0F, "", "")
            )
        )
    }
}


