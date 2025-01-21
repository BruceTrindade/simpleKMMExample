package org.example.domain.model

data class Movie(
    val id: Int,
    val overview: String? = null,
    val posterPath: String? = null,
    val title: String? = null,
)
