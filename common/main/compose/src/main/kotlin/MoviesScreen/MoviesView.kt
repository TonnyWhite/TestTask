package MoviesScreen


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import models.Movie
import movies.models.MoviesViewState
import movies.models.MoviesEvent
import theme.Theme.colors
import widgets.MovieListDescription
import widgets.MovieListPoster
import widgets.MovieListTitle


@Composable
fun MoviesView(
    state: MoviesViewState,
    eventHandler: (MoviesEvent) -> Unit,
) {

    Column(modifier = Modifier.fillMaxWidth()) {
        Row {
            MovieListContent(state, eventHandler)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun MovieListContent(
    state: MoviesViewState,
    eventHandler: (MoviesEvent) -> Unit,
) {
    val listState = rememberLazyListState()

    LazyColumn(state = listState) {
        items(state.movies) { movie ->
            MovieItem(movie = movie, modifier = Modifier.animateItemPlacement()) { selectedMovie ->
                eventHandler.invoke(MoviesEvent.MovieClicked(selectedMovie))
            }
        }
    }

    val scrollContext = rememberScrollContext(listState)


}


@Composable
private fun MovieItem(
    movie: Movie,
    modifier: Modifier = Modifier,
    onMovieSelected: (Movie) -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardColors(colors.tagColor, colors.thirdTextColor, colors.tagColor, colors.thirdTextColor),
        onClick = { onMovieSelected(movie) }
    ) {
        Row {
            MovieListPoster(
                posterUrl = movie.posterUrl,
                modifier = Modifier
                    .padding(12.dp)
                    .width(100.dp)

            )
            Spacer(modifier = Modifier.height(12.dp))
            Column(modifier = modifier.weight(1f)) {
                MovieListTitle(
                    title = movie.title,
                    modifier = Modifier.padding(top = 12.dp)
                )
                MovieListDescription(
                    title = movie.overview,
                    modifier = Modifier.padding(bottom = 12.dp, top = 12.dp)
                )

            }

        }
    }
}


