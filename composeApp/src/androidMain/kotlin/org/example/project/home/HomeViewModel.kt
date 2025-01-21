package org.example.project.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.example.domain.repository.MoviesRepository
import org.example.project.RequestState

class HomeViewModel(private val repository: MoviesRepository) : ViewModel() {

    private val _moviesState = MutableStateFlow<RequestState>(RequestState.Loading)
    val moviesState: StateFlow<RequestState> = _moviesState

    fun fetchMovies() {
        viewModelScope.launch {
            try {
                _moviesState.value = RequestState.Loading

                val result = repository.fetchPopularMovies()

                result.collect { response ->
                    _moviesState.value = response
                }
            } catch (e: Exception) {
                _moviesState.value = RequestState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }
}
