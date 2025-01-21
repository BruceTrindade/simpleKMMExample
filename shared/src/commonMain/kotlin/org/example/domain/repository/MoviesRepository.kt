package org.example.domain.repository

import kotlinx.coroutines.flow.Flow
import org.example.project.RequestState

interface MoviesRepository {

    fun fetchPopularMovies(): Flow<RequestState>
}
