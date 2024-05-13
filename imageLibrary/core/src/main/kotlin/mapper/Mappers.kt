package mapper


import io.ktor.http.Url
import java.net.URI
import java.net.URL

import kotlin.reflect.KClass

internal val StringMapper: Mapper<String, Url> = object : Mapper<String, Url> {
    override val inputKClass: KClass<String>
        get() = String::class
    override val outputKClass: KClass<Url>
        get() = Url::class

    override fun map(input: String): Url = Url(input)

}

val URLMapper: Mapper<URL, Url> = object : Mapper<URL, Url> {
    override val inputKClass: KClass<URL>
        get() = URL::class
    override val outputKClass: KClass<Url>
        get() = Url::class

    override fun map(input: URL): Url {
        return Url(input.toString())//todo think
    }

}

val URIMapper: Mapper<URI, Url> = object : Mapper<URI, Url> {
    override val inputKClass: KClass<URI>
        get() = URI::class
    override val outputKClass: KClass<Url>
        get() = Url::class

    override fun map(input: URI): Url {
        return Url(input.toString())
    }
}