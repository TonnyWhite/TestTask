package ktor.models

import ErrorType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import models.ClientException
import models.GenericException
import models.Movie
import models.Movies
import models.ServerException
import models.UnauthorizedException
import java.io.IOException
import java.net.HttpURLConnection

@Serializable
data class MoviesResponse(
    @SerialName("page") val page: Int,
    @SerialName("results") val results: List<MovieResponse>,
    @SerialName("total_pages") val totalPages: Int
)

@Serializable
data class MovieResponse(
    @SerialName("id") val id: Int,
    @SerialName("original_title") val originalTitle: String,
    @SerialName("title") val title: String,
    @SerialName("overview") val overview: String,
    @SerialName("poster_path") val posterPath: String,
    @SerialName("backdrop_path") val backdropPath: String?,
    @SerialName("release_date") val releaseDate: String
)

fun MoviesResponse.toMovies(): Movies =
    Movies(
        currentPage = page,
        totalPages = totalPages,
        movies = results.map { it.toDomainModel() }
    )

fun MovieResponse.toDomainModel(): Movie = Movie(
    id = id,
    title = title,
    overview = overview,
    posterUrl = "https://image.tmdb.org/t/p/w185$posterPath",
    backdropUrl = "https://image.tmdb.org/t/p/w780$backdropPath",
    releaseDate = releaseDate
)

fun mapResponseCodeToThrowable(code: Int): Throwable = when (code) {
    HttpURLConnection.HTTP_UNAUTHORIZED -> UnauthorizedException("Unauthorized access : $code")
    in 400..499 -> ClientException("Client error : $code")
    in 500..600 -> ServerException("Server error : $code")
    else -> GenericException("Generic error : $code")
}

fun Throwable.toErrorType(): ErrorType = when (this) {
    is IOException -> ErrorType.IO_CONNECTION
    is ClientException -> ErrorType.CLIENT
    is ServerException -> ErrorType.SERVER
    is UnauthorizedException -> ErrorType.UNAUTHORIZED
    else -> ErrorType.GENERIC
}

