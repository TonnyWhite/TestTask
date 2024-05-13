package movies.models

import models.Movie

sealed class MoviesAction {
    data class ShowMovieDetail(var movie: Movie) : MoviesAction()
}