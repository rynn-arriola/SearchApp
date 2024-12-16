package com.example.rynnarriola.searchapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rynnarriola.searchapp.data.model.Item
import com.example.rynnarriola.searchapp.data.repository.FlickrRepo
import com.example.rynnarriola.searchapp.util.Constants.DEBOUNCE_TIMEOUT
import com.example.rynnarriola.searchapp.util.Constants.MIN_SEARCH_CHAR
import com.example.rynnarriola.searchapp.util.DispatcherProvider
import com.example.rynnarriola.searchapp.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FlickrViewModel @Inject constructor(
    private val repo: FlickrRepo,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Item>>>(UiState.Success(emptyList()))
    val uiState: StateFlow<UiState<List<Item>>> = _uiState

    private val query = MutableStateFlow("")

    init {
        observeQueryChanges()
    }

    fun searchImage(searchQuery: String) {
        query.value = searchQuery
        // Handle empty or short queries outside the flow
        if (searchQuery.isEmpty() || searchQuery.length < MIN_SEARCH_CHAR) {
            _uiState.value = UiState.Success(emptyList())
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    fun observeQueryChanges() {
        viewModelScope.launch(dispatcherProvider.main) {
            query.debounce(DEBOUNCE_TIMEOUT)
                .filter { it.isNotEmpty() && it.length >= MIN_SEARCH_CHAR }
                .distinctUntilChanged()
                .flatMapLatest { searchQuery ->
                    _uiState.value = UiState.Loading
                    repo.getSearchFlickr(searchQuery)
                        .catch {
                            _uiState.value =
                                UiState.Error("Something went wrong. Please try again.")
                        }
                }
                .flowOn(dispatcherProvider.io)
                .collect { items ->
                    _uiState.value = if (items.isEmpty()) {
                        UiState.Error("No results found.")
                    } else {
                        UiState.Success(items)
                    }
                }
        }
    }
}