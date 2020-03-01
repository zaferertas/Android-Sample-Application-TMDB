package com.xxxxx.sampleapplicationtmdb.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.xxxxx.sampleapplicationtmdb.data.MovieItem
import com.xxxxx.sampleapplicationtmdb.data.MoviePageResult
import com.xxxxx.sampleapplicationtmdb.data.Repository
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MainViewModelTest {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    private val repository: Repository = mock()
    private val compositeDisposable: CompositeDisposable = mock()

    private val observerIsLoading = mock<Observer<Boolean>>()
    private val observerMovieItems = mock<Observer<List<MovieItem>>>()
    private val observerErrorLoadingList = mock<Observer<Boolean>>()

    private val mockedMoviePageResult = mock<MoviePageResult>()

    private lateinit var mainViewModel: MainViewModel

    @Before
    fun setUp() {
        mainViewModel = MainViewModel(repository, compositeDisposable)
    }

    @Test
    fun `isLoading shows correct state before and after getPopularMovies executes with success`() {

        mainViewModel.isLoading.observeForever(observerIsLoading)

        Assert.assertEquals(true, mainViewModel.isLoading.value)

        argumentCaptor<DisposableSingleObserver<MoviePageResult>>().apply {
            verify(repository).getPopularMovies(capture())
            firstValue.onSuccess(mockedMoviePageResult)
            Assert.assertEquals(false, mainViewModel.isLoading.value)
        }
    }


    @Test
    fun `movieItems gets populated when loadPopularMovies executes successfully`() {

        val mockedMoviePage = MoviePageResult(
            page = 1,
            totalResults = 1,
            totalPages = 1,
            movieResult = arrayListOf(
                MovieItem(0, ", 1.0, ", 1.0F, "", "")
            )
        )

        mainViewModel.movieItems.observeForever(observerMovieItems)

        Assert.assertEquals(true, mainViewModel.isLoading.value)

        argumentCaptor<DisposableSingleObserver<MoviePageResult>>().apply {
            verify(repository).getPopularMovies(capture())
            firstValue.onSuccess(mockedMoviePage)
            Assert.assertEquals(1, mainViewModel.movieItems.value?.size)
        }
    }

    @Test
    fun `errorLoadingList gets populated when loadPopularMovies executes with error`() {

        mainViewModel.errorLoadingList.observeForever(observerErrorLoadingList)

        argumentCaptor<DisposableSingleObserver<MoviePageResult>>().apply {
            verify(repository).getPopularMovies(capture())
            firstValue.onError(Exception())
            Assert.assertEquals(true, mainViewModel.errorLoadingList.value)
        }
    }

    @After
    fun tearDown() {
        mainViewModel.movieItems.removeObserver(observerMovieItems)
        mainViewModel.isLoading.removeObserver(observerIsLoading)
        mainViewModel.errorLoadingList.removeObserver(observerErrorLoadingList)
    }

}


