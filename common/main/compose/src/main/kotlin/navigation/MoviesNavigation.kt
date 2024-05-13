package navigation


import MoviesDetailsScreen
import MoviesScreen.MoviesScreen
import models.Movie
import navigation.NavigationTree
import ru.alexgladkov.odyssey.compose.extensions.flow
import ru.alexgladkov.odyssey.compose.extensions.screen
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder


fun RootComposeBuilder.mainFlow() {
    flow(name = NavigationTree.Main.Movies.name) {
        screen(name = NavigationTree.Main.Movies.name) {
            MoviesScreen()
        }

        screen(name = NavigationTree.Main.Details.name){
            MoviesDetailsScreen(it as Movie)
        }


    }
}