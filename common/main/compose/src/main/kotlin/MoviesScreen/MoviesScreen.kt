package MoviesScreen


import androidx.compose.runtime.Composable
import com.adeo.kviewmodel.compose.observeAsState
import com.adeo.kviewmodel.odyssey.StoredViewModel
import navigation.NavigationTree
import ru.alexgladkov.odyssey.compose.extensions.push
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import movies.MoviesViewModel
import movies.models.MoviesAction


@Composable
fun MoviesScreen() {

    val rootController = LocalRootController.current


    StoredViewModel(factory = { MoviesViewModel() }) { viewModel ->
        val state = viewModel.viewStates().observeAsState()
        val actions = viewModel.viewActions().observeAsState()
        MoviesView(state.value) {
            viewModel.obtainEvent(it)
        }

        when (actions.value) {
            is MoviesAction.ShowMovieDetail -> rootController.push(
                NavigationTree.Main.Details.name, params = (actions.value as MoviesAction.ShowMovieDetail).movie
            )

            null -> {}
        }
    }
}


