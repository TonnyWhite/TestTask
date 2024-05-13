package models

data class MoviesRequest(
    val language: String,
    val page: Int,
    val sortBy: String,
    val releaseDateEnd: String,
    val releaseDateStart: String,
)