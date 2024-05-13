import android.content.Context
import di.Inject
import ktor.moviesModule
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.direct
import org.kodein.di.singleton


object PlatformSDK {

    fun init(
        configuration: Context
    ) {
        val umbrellaModule = DI.Module(
            name = "umbrella",
            init = {
                bind<Context>() with singleton { configuration }
            }
        )

        Inject.createDependencies(
            DI {
                importAll(
                    umbrellaModule,
                    coreModule,
                    moviesModule,
                )
            }.direct
        )
    }
}