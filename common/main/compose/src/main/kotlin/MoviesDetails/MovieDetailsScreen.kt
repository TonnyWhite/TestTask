import MoviesDetails.MovieDetailsView
import MoviesScreen.MoviesView
import androidx.compose.runtime.Composable
import com.adeo.kviewmodel.compose.observeAsState
import com.adeo.kviewmodel.odyssey.StoredViewModel
import detatils.MovieDetailsViewModel
import detatils.models.MovieDetailsAction
import models.Movie
import navigation.NavigationTree
import ru.alexgladkov.odyssey.compose.extensions.push
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import movies.MoviesViewModel
import movies.models.MoviesAction

@Composable
fun MoviesDetailsScreen(movie: Movie) {

    val rootController = LocalRootController.current


    StoredViewModel(factory = { MovieDetailsViewModel(movie) }) { viewModel ->
        val state = viewModel.viewStates().observeAsState()
        val actions = viewModel.viewActions().observeAsState()
        MovieDetailsView(state.value) {
            viewModel.obtainEvent(it)
        }

        when (actions.value) {
            is MovieDetailsAction.Back -> rootController.push(
                NavigationTree.Main.Movies.name
            )

            null -> {}
        }
    }
}

