import models.Movies
import models.MoviesRequest

interface MoviesRepository {
    suspend fun fetchAllMovies(movieParams: MoviesRequest) : Movies
}