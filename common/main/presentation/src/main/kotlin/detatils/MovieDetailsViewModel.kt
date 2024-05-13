package detatils

import com.adeo.kviewmodel.BaseSharedViewModel
import detatils.models.MovieDetailsAction
import detatils.models.MovieDetailsEvent
import detatils.models.MovieDetailsViewState
import models.Movie

class MovieDetailsViewModel(
    movie: Movie,
) : BaseSharedViewModel<MovieDetailsViewState, MovieDetailsAction, MovieDetailsEvent>(
    initialState = MovieDetailsViewState(movie)
) {
    override fun obtainEvent(viewEvent: MovieDetailsEvent) {
        when (viewEvent) {
            is MovieDetailsEvent.BackClick -> back()
            is MovieDetailsEvent.MovieShow -> TODO()
            else -> {}
        }
    }



    private fun back() {
        viewAction = MovieDetailsAction.Back
    }
}