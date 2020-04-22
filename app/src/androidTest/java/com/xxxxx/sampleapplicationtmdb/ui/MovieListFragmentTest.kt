package com.xxxxx.sampleapplicationtmdb.ui


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.xxxxx.sampleapplicationtmdb.R
import com.xxxxx.sampleapplicationtmdb.data.MovieItem
import com.xxxxx.sampleapplicationtmdb.ui.movielist.MovieListAdapter
import com.xxxxx.sampleapplicationtmdb.ui.movielist.MovieListFragment
import com.xxxxx.sampleapplicationtmdb.ui.movielist.MovieListViewModel
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@MediumTest
class MovieListFragmentTest {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    private val mainViewModelFactory: MainViewModelFactory = mockk(relaxed = true)
    private val movieListViewModel: MovieListViewModel = mockk(relaxed = true)

    private val loadingLiveData = MutableLiveData<Boolean>()
    private val errorLoadingListLiveData = MutableLiveData<Boolean>()
    private val movieItemsLiveData = MutableLiveData<List<MovieItem>>()

    @Before
    fun setUp() {

        every { movieListViewModel.isLoading } returns loadingLiveData
        every { movieListViewModel.movieItems } returns movieItemsLiveData
        every { movieListViewModel.errorLoadingList } returns errorLoadingListLiveData

        every { mainViewModelFactory.create(MovieListViewModel::class.java) } returns movieListViewModel
    }

    @Test
    fun testLoadingProgressBarVisible() {
        loadingLiveData.postValue(true)
        launchFragment()
        onView(withId(R.id.main_progress_bar)).check(matches(isDisplayed()))
    }

    @Test
    fun testAErrorLoadingListTextVisible() {
        errorLoadingListLiveData.postValue(true)
        launchFragment()
        onView(withId(R.id.main_fetch_error)).check(matches(isDisplayed()))
    }

    @Test
    fun testListItemsVisible() {
        movieItemsLiveData.postValue(fakeList)
        launchFragment()
        onView(withId(R.id.main_recycler_view)).check(matches(isDisplayed()))

//        onView(withId(R.id.main_recycler_view))
//            .check(matches(atPosition(0, hasDescendant(withText("Test Text")))));
//        onView(withId(R.id.title)).check(matches(withText("Hello World from Mock")))

        //onData(allOf(`is`(instanceOf(MovieItem::class.java)), `is`("First Item")))
        //onView(withId(R.id.main_recycler_view)).check(matches(hasDescendant(withText("First Item"))))
        onView(withText("First Item")).check(matches(isDisplayed()))
    }


    @Test
    fun testListItemClick() {
        movieItemsLiveData.postValue(fakeList)
        launchFragment()

        val recyclerView = onView(withId(R.id.main_recycler_view))
        recyclerView.perform(
            RecyclerViewActions.scrollToPosition<MovieListAdapter.ItemViewHolder>(1)
        )
        recyclerView.perform(
            RecyclerViewActions
                .actionOnItemAtPosition<MovieListAdapter.ItemViewHolder>(
                    1,
                    click()
                )
        )

        // verify(viewModel, times(1)).plantItemClicked()

    }

    private val fakeList = listOf(
        MovieItem(0, "First Item", 1.0F, "", ""),
        MovieItem(1, "Second Item", 1.0F, "", ""),
        MovieItem(2, "Third Item", 1.0F, "", "")
    )

    private fun launchFragment() {
        launchFragmentInContainer<MovieListFragment>(
            factory = MainFragmentFactory(mainViewModelFactory)
        )
    }


}