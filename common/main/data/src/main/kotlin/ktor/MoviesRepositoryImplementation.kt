package ktor

import MoviesRepository
import ktor.models.toMovies

import models.Movies
import models.MoviesRequest

class MoviesRepositoryImplementation(
    private val remoteDataSource: KtorMoviesRemoteDataSource
) : MoviesRepository {
    override suspend fun fetchAllMovies(movieParams: MoviesRequest): Movies {
        return remoteDataSource.getMovies(movieParams).toMovies()
    }

}