package movies.models

import models.Movie

sealed class MoviesEvent {
    object MoviesShow : MoviesEvent()
    data class MovieClicked(val movie: Movie) : MoviesEvent()
}