package ktor


import MoviesRepository
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.provider
import org.kodein.di.singleton


val moviesModule = DI.Module("moviesModule") {
    bind<KtorMoviesRemoteDataSource>() with provider {
        KtorMoviesRemoteDataSource(instance())
    }

    bind<MoviesRepository>() with singleton {
        MoviesRepositoryImplementation(instance())
    }
}