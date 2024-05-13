package ktor

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.path
import ktor.models.MoviesResponse
import models.MoviesRequest


class KtorMoviesRemoteDataSource(
    private val httpClient: HttpClient,
) {
    suspend fun getMovies(request: MoviesRequest): MoviesResponse {
        return httpClient.get {
            url {
                path("discover/movie")
                parameters.append("language", request.language)
                parameters.append("page", request.page.toString())
                parameters.append("sortBy", request.sortBy)
                parameters.append("releaseDataEns", request.releaseDateEnd)
                parameters.append("releaseDateStart", request.releaseDateStart)
            }
        }.body()
    }
}