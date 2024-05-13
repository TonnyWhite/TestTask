package movies


import MoviesRepository
import com.adeo.kviewmodel.BaseSharedViewModel
import di.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import models.Movie
import models.MoviesRequest
import movies.models.MoviesAction
import movies.models.MoviesEvent
import movies.models.MoviesViewState

class MoviesViewModel : BaseSharedViewModel<MoviesViewState, MoviesAction, MoviesEvent>(
    initialState = MoviesViewState()
) {
    private val movieRepository: MoviesRepository = Inject.instance()
    private var searchJob: Job? = null

    init {
        showMovie()
    }
    override fun obtainEvent(viewEvent: MoviesEvent) {
        when(viewEvent) {
            is MoviesEvent.MovieClicked -> showMovies(viewEvent.movie)
            is MoviesEvent.MoviesShow -> showMovie()
            else -> {}
        }
    }

    private fun showMovie() {
       searchJob = viewModelScope.launch {
           viewState.copy()
           searchJob?.cancel()
           delay(500)
           viewState = try {
               val moviesResponse = movieRepository.fetchAllMovies(MoviesRequest("ru", 1, "", "", "popularity.desc"))
               viewState.copy(movies = moviesResponse.movies)
           } catch (e: Exception) {
               viewState.copy(movies = emptyList())
           }

       }
    }

    private fun showMovies(movie: Movie) {
        viewAction = MoviesAction.ShowMovieDetail(movie)
    }
}