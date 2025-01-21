package org.example.data.repository

import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.example.data.MovieResultsDto
import org.example.data.remote.MoviesApi
import org.example.domain.model.Movie
import org.example.domain.repository.MoviesRepository
import org.example.project.RequestState

class MoviesRepositoryImpl(
    private val moviesApi: MoviesApi,
) : MoviesRepository {

    override fun fetchPopularMovies(): Flow<RequestState> {
        return flow {
            emit(RequestState.Loading)
            try {
                val moviesDto =
                    moviesApi.httpClient.get("https://api.themoviedb.org/3/movie/popular") {
                        parameter("api_key", "a11ea8d934b139ebc6037e66c7d311ea")
                    }.body<MovieResultsDto>().movies

                val movies = moviesDto.orEmpty().map { dto ->
                    Movie(
                        id = dto.id,
                        title = dto.title,
                        overview = dto.overview,
                        posterPath = dto.posterPath,
                    )
                }

                emit(RequestState.Success(data = movies))
            } catch (e: Exception) {
                emit(RequestState.Error(message = "Error while fetching the data."))
            }
        }
    }
}
