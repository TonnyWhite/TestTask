package utils

import io.ktor.http.*

internal val Url.path: String get() = encodedPath.removePrefix("/")