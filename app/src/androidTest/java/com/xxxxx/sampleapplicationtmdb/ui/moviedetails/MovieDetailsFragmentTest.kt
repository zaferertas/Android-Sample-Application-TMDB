package com.xxxxx.sampleapplicationtmdb.ui.moviedetails

import android.os.Bundle
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.xxxxx.sampleapplicationtmdb.MOVIE_ID
import com.xxxxx.sampleapplicationtmdb.R
import com.xxxxx.sampleapplicationtmdb.data.Genres
import com.xxxxx.sampleapplicationtmdb.data.MovieDetails
import com.xxxxx.sampleapplicationtmdb.ui.MainFragmentFactory
import com.xxxxx.sampleapplicationtmdb.ui.MainViewModelFactory
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@MediumTest
class MovieDetailsFragmentTest {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    private val mainViewModelFactory: MainViewModelFactory = mockk(relaxed = true)
    private val movieDetailsViewModel: MovieDetailsViewModel = mockk(relaxed = true)

    private val loadingLiveData = MutableLiveData<Boolean>()
    private val errorLoadingDetailsLiveData = MutableLiveData<Boolean>()
    private val selectedMovieLiveData = MutableLiveData<MovieDetails>()

    @Before
    fun setUp() {

        every { movieDetailsViewModel.isLoading } returns loadingLiveData
        every { movieDetailsViewModel.selectedMovie } returns selectedMovieLiveData
        every { movieDetailsViewModel.errorLoadingDetails } returns errorLoadingDetailsLiveData

        every { mainViewModelFactory.create(MovieDetailsViewModel::class.java) } returns movieDetailsViewModel
    }

    @Test
    fun testLoadingProgressBarVisible() {
        loadingLiveData.postValue(true)
        launchFragment()

        onView(withId(R.id.details_progress_bar)).check(matches(isDisplayed()))
    }

    @Test
    fun testErrorLoadingDetailsTextVisible() {
        errorLoadingDetailsLiveData.postValue(true)
        launchFragment()

        onView(withId(R.id.details_fetch_error)).check(matches(isDisplayed()))
    }

    @Test
    fun testMovieDetailsVisible() {
        selectedMovieLiveData.postValue(fakeMovieDetails)
        launchFragment()

        onView(withId(R.id.tagline)).check(matches(withText("Movie tagline")))
    }

    private val fakeMovieDetails = MovieDetails(
        adult = true,
        backdropPath = "",
        budget = 1,
        genres = arrayListOf<Genres>(),
        homepage = "",
        id = 0,
        imdbId = "",
        originaLanguage = "",
        originalTitle = "Movie Title",
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
        tagline = "Movie tagline",
        title = "",
        video = true,
        voteAverage = 1.0,
        voteCount = 1
    )

    private fun launchFragment() {

        val fragmentArgs = Bundle().apply {
            putInt(MOVIE_ID, 0)
        }

        val factory = MainFragmentFactory(
            mainViewModelFactory
        )
        launchFragmentInContainer<MovieDetailsFragment>(
            fragmentArgs = fragmentArgs,
            factory = factory
        )
    }
}