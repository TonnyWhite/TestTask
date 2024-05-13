package navigation

import SplashScreen
import navigation.mainFlow
import ru.alexgladkov.odyssey.compose.extensions.screen
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder

fun RootComposeBuilder.generateGraph(){
    screen(name = NavigationTree.Splash.SplashScreen.name){
        SplashScreen()
    }
    mainFlow()

}