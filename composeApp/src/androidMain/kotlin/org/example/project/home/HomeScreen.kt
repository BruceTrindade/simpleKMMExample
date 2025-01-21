package org.example.project.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.example.data.remote.MoviesApi
import org.example.data.repository.MoviesRepositoryImpl
import org.example.project.MovieCard
import org.example.project.RequestState

@Composable
fun HomeScreen() {
    val repository = remember { MoviesRepositoryImpl(MoviesApi()) }
    val viewModel = remember { HomeViewModel(repository) }

    LaunchedEffect(Unit) {
        viewModel.fetchMovies()
    }

    val moviesState by viewModel.moviesState.collectAsState()

    when (val state = moviesState) {
        is RequestState.Success -> {
            val movies = state.data
            LazyColumn {
                items(movies) { movie ->
                    MovieCard(movie = movie)
                }
            }
        }
        is RequestState.Error -> {
            val error = state.message
            Text(text = error, color = Color.Red)
        }
        else -> {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }
        }
    }
}
