package movies.models


import models.Movie

data class MoviesViewState(
    val movies: List<Movie> = emptyList()
)