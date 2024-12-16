package com.example.rynnarriola.searchapp

import app.cash.turbine.test
import com.example.rynnarriola.searchapp.data.model.Item
import com.example.rynnarriola.searchapp.data.model.Media
import com.example.rynnarriola.searchapp.data.repository.FlickrRepo
import com.example.rynnarriola.searchapp.util.DispatcherProvider
import com.example.rynnarriola.searchapp.util.UiState
import com.example.rynnarriola.searchapp.viewmodel.FlickrViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class FlickrViewModelTest {

    @Mock
    private lateinit var repo: FlickrRepo

    private lateinit var dispatcherProvider: DispatcherProvider

    @Before
    fun setUp() {
        dispatcherProvider = TestDispatcherProvider() // Custom TestDispatcherProvider for testing
        Dispatchers.setMain(dispatcherProvider.main)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun searchImage_whenValidQuery_shouldUpdateUiStateWithSuccess() = runTest {

        // Mock repository method to return the mocked items
        doReturn(flowOf(emptyList<Item>()))
            .`when`(repo)
            .getSearchFlickr("kotlin")

        // Act: Initialize the ViewModel and observe the UI state
        val viewModel = FlickrViewModel(repo, dispatcherProvider)

        // Start observing the UI state and perform the action
        viewModel.uiState.test {
            // Call the searchImage function with a valid query
            viewModel.searchImage("kotlin")

            // Ensure all coroutines complete and the flow emits the updated state
            advanceUntilIdle()

            // Assert: Verify that the UI state is updated to Success with the list
            assertEquals(UiState.Success(emptyList<List<Item>>()), awaitItem())
            //Ensure no more events is emitted
            cancelAndIgnoreRemainingEvents()
        }

        // Verify that the repository's getSearchFlickr was called exactly once
        verify(repo, times(1)).getSearchFlickr("kotlin")
    }
}
