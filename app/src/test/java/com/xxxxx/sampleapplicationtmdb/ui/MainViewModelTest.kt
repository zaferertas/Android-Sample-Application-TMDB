package com.xxxxx.sampleapplicationtmdb.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.xxxxx.sampleapplicationtmdb.data.Repository
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class MainViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    val repository = Mockito.mock(Repository::class.java)

    val mainViewModel =
        MainViewModel(repository)

    @Test
    fun testxxx () {

    }
}