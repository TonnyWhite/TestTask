package MoviesDetails


import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import detatils.models.MovieDetailsEvent
import detatils.models.MovieDetailsViewState
import models.Movie
import theme.Theme
import widgets.MovieDetailsBackDrop
import widgets.MovieDetailsDescription
import widgets.MovieDetailsReleaseDate
import widgets.MovieDetailsTitle
import widgets.TopBar


@Composable
fun MovieDetailsView(
    state: MovieDetailsViewState,
    eventHandler: (MovieDetailsEvent) -> Unit,
) {
    Scaffold(
        modifier = Modifier.background(Theme.colors.primaryBackground),
        topBar = {
            TopBar(onBackPressed = { eventHandler.invoke(MovieDetailsEvent.BackClick) })
        }


    ) { padding ->
        AnimatedContent(targetState = state, label = "Movie Detail Animate") { state ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(Theme.colors.primaryBackground)
                    .padding(padding)
            ) {

                MovieDetailsContent(state.movie)
            }
        }
    }
}

@Composable
private fun MovieDetailsContent(movie: Movie) {
    movie.backdropUrl?.let { backDrop ->
        MovieDetailsBackDrop(
            backDropUrl = backDrop,
            modifier = Modifier
                .fillMaxWidth()

        )
    }
    movie.title?.let { title ->
        MovieDetailsTitle(
            title = title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp, vertical = 8.dp
                )
        )
    }
    movie.overview?.let { overview ->
        MovieDetailsDescription(
            title = overview,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp, vertical = 8.dp
                )
        )
    }
    movie.releaseDate?.let { releaseDate ->
        MovieDetailsReleaseDate(
            releaseDate = releaseDate,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp, vertical = 8.dp
                )
        )
    }
}
//
