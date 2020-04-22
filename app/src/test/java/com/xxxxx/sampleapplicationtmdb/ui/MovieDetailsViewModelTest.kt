package com.xxxxx.sampleapplicationtmdb.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.xxxxx.sampleapplicationtmdb.data.Genres
import com.xxxxx.sampleapplicationtmdb.data.MovieDetails
import com.xxxxx.sampleapplicationtmdb.data.Repository
import com.xxxxx.sampleapplicationtmdb.ui.moviedetails.MovieDetailsViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers.anyInt

@RunWith(JUnit4::class)
class MovieDetailsViewModelTest {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    private val repository: Repository = mock()

    private val observerIsLoading = mock<Observer<Boolean>>()
    private val observerSelectedMovie = mock<Observer<MovieDetails>>()
    private val observerErrorLoadingDetails = mock<Observer<Boolean>>()

    private lateinit var movieDetailsViewModel: MovieDetailsViewModel
    private val compositeDisposable: CompositeDisposable = mock()

    @Before
    fun setUp() {
        movieDetailsViewModel =
            MovieDetailsViewModel(
                repository,
                compositeDisposable
            )
    }

    @Test
    fun `isLoading shows correct state before and after loadMovieDetails executes with success`() {

        movieDetailsViewModel.isLoading.observeForever(observerIsLoading)

        movieDetailsViewModel.loadMovieDetails(anyInt())

        Assert.assertEquals(true, movieDetailsViewModel.isLoading.value)

        argumentCaptor<DisposableSingleObserver<MovieDetails>>().apply {
            verify(repository).getMovieDetails(anyInt(), capture())
            firstValue.onSuccess(getDummyMovieDetailsObject())
            Assert.assertEquals(false, movieDetailsViewModel.isLoading.value)
        }
    }

    @Test
    fun `selectedMovie gets populated when loadMovieDetails executes successfully`() {
        movieDetailsViewModel.isLoading.observeForever(observerIsLoading)
        movieDetailsViewModel.selectedMovie.observeForever(observerSelectedMovie)

        movieDetailsViewModel.loadMovieDetails(anyInt())

        argumentCaptor<DisposableSingleObserver<MovieDetails>>().apply {
            verify(repository).getMovieDetails(anyInt(), capture())
            val movieDetails = getDummyMovieDetailsObject()
            firstValue.onSuccess(movieDetails)
            Assert.assertEquals(movieDetails, movieDetailsViewModel.selectedMovie.value)
        }
    }

    @Test
    fun `errorLoadingDetails shows true when loadMovieDetails executes with error`() {

        movieDetailsViewModel.isLoading.observeForever(observerIsLoading)
        movieDetailsViewModel.errorLoadingDetails.observeForever(observerErrorLoadingDetails)

        movieDetailsViewModel.loadMovieDetails(anyInt())

        Assert.assertEquals(false, movieDetailsViewModel.errorLoadingDetails.value)

        argumentCaptor<DisposableSingleObserver<MovieDetails>>().apply {
            verify(repository).getMovieDetails(anyInt(), capture())
            firstValue.onError(Exception())
            Assert.assertEquals(true, movieDetailsViewModel.errorLoadingDetails.value)
        }
    }

    @After
    fun tearDown() {
        movieDetailsViewModel.isLoading.removeObserver(observerIsLoading)
        movieDetailsViewModel.selectedMovie.removeObserver(observerSelectedMovie)
        movieDetailsViewModel.errorLoadingDetails.removeObserver(observerErrorLoadingDetails)
    }

    private fun getDummyMovieDetailsObject(): MovieDetails {

        return MovieDetails(
            adult = true,
            backdropPath = "",
            budget = 1,
            genres = arrayListOf<Genres>(),
            homepage = "",
            id = 0,
            imdbId = "",
            originaLanguage = "",
            originalTitle = "",
            overview = "",
            popularity = 1.0,
            posterPath = "",
            productionCompanies = arrayListOf(),
            productionCountries = arrayListOf(),
            releaseDate = "",
            revenue = 0,
            runtime = 0,
            spokenLanguages = arrayListOf(),
            status = "",
            tagline = "",
            title = "",
            video = true,
            voteAverage = 1.0,
            voteCount = 1
        )
    }

}