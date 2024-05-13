package utils

import java.net.URI
import java.net.URL


fun createURI(url: String): URI {
    return URI(url)
}

fun createURL(url: String): URL {
    return URL(url)
}