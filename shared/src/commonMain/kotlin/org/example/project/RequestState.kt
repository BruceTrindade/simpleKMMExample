package org.example.project

import kotlinx.serialization.Serializable
import org.example.domain.model.Movie

@Serializable
sealed class RequestState {
    data object Loading : RequestState()
    data class Success(val data: List<Movie>) : RequestState()
    data class Error(val message: String) : RequestState()

    fun isLoading(): Boolean = this is Loading
    fun isSuccess(): Boolean = this is Success
    fun isError(): Boolean = this is Error

    fun getMovies(): List<Movie> = (this as Success).data

    fun getErrorMessage(): String = (this as Error).message
}
