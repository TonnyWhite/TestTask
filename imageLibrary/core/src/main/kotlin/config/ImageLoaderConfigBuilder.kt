@file:Suppress("UNCHECKED_CAST")

package config

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import cache.Cache
import utils.LruCache
import cache.httpCacheStorage
import decoder.Decoder
import fetcher.Fetcher
import fetcher.HttpFetcher
import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.request.*
import io.ktor.http.*
import mapper.Mapper
import mapper.StringMapper
import mapper.URIMapper
import mapper.URLMapper
import kotlin.reflect.KClass
 class ImageLoaderConfigBuilder {

    internal val fetchers: MutableList<Fetcher<Any>> = mutableListOf()

    internal val decoders: MutableList<Decoder<Any>> = mutableListOf()

    internal val mappers: MutableMap<KClass<*>, MutableList<Mapper<Any, Any>>> = mutableMapOf()

    var imageBitmapCacheSize: Int = 0

     var imageVectorCacheSize: Int = 0

     var svgCacheSize: Int = 0

     fun <T : Any> fetcher(fetcher: Fetcher<T>) {
        fetchers += fetcher as Fetcher<Any>
    }

     fun <T : Any> decoder(decoder: Decoder<T>) {
        decoders += decoder as Decoder<Any>
    }

     fun <I : Any, O : Any> mapper(mapper: Mapper<I, O>) {
        mappers.getOrPut(mapper.inputKClass) { mutableListOf() }.add(mapper as Mapper<Any, Any>)
    }

     fun build(): ImageLoaderConfig = object : ImageLoaderConfig {

        override val fetchers: List<Fetcher<Any>> = this@ImageLoaderConfigBuilder.fetchers

        override val decoders: List<Decoder<Any>> = this@ImageLoaderConfigBuilder.decoders

        override val mappers: Map<KClass<*>, List<Mapper<Any, Any>>> =
            this@ImageLoaderConfigBuilder.mappers

        override val imageBitmapCache: Cache<Any, ImageBitmap> = LruCache(imageBitmapCacheSize)

        override val imageVectorCache: Cache<Any, ImageVector> = LruCache(imageVectorCacheSize)

        override val svgCache: Cache<Any, Painter> = LruCache(svgCacheSize)
    }

     fun HttpClientConfig<*>.httpCache(size: Long): Unit = install(HttpCache) {
        publicStorage(httpCacheStorage(size))
    }
}

/**
 * Adds an Http fetcher to the [ImageLoaderConfigBuilder] using the specified [client].
 */
 fun ImageLoaderConfigBuilder.httpFetcher(client: HttpClient): Unit = fetcher(HttpFetcher(client))

/**
 * Adds an Http fetcher to the [ImageLoaderConfigBuilder] using the specified [engine]
 * and an optional [block] for configuring this client.
 */
 fun ImageLoaderConfigBuilder.httpFetcher(
    engine: HttpClientEngine,
    block: HttpClientConfig<*>.() -> Unit = {}
): Unit = fetcher(HttpFetcher(HttpClient(engine, block)))

/**
 * Adds an Http fetcher to the [ImageLoaderConfigBuilder] by loading an [HttpClientEngine] from [ServiceLoader]
 * and an optional [block] for configuring this client.
 */
 fun ImageLoaderConfigBuilder.httpFetcher(
    block: HttpClientConfig<*>.() -> Unit = {}
): Unit = fetcher(HttpFetcher(HttpClient(block)))

/**
 * Adds a [File] fetcher to the [ImageLoaderConfigBuilder].
 */
// fun ImageLoaderConfigBuilder.fileFetcher(): Unit = fetcher()

/**
 * Adds a [String] to [Url] mapper to the [ImageLoaderConfigBuilder].
 */
 fun ImageLoaderConfigBuilder.stringMapper(): Unit = mapper(StringMapper)

/**
 * Adds a [URI] to [Url] mapper to the [ImageLoaderConfigBuilder].
 */
 fun ImageLoaderConfigBuilder.uriMapper(): Unit = mapper(URIMapper)

/**
 * Adds a [URL] to [Url] mapper to the [ImageLoaderConfigBuilder].
 */
 fun ImageLoaderConfigBuilder.urlMapper(): Unit = mapper(URLMapper)

/**
 * Copies all the data from [builder] and uses it as base for [this].
 */
 fun ImageLoaderConfigBuilder.takeFrom(builder: ImageLoaderConfigBuilder): ImageLoaderConfigBuilder =
    takeFrom(builder.build())

/**
 * Copies all the data from [config] and uses it as base for [this].
 */
fun ImageLoaderConfigBuilder.takeFrom(config: ImageLoaderConfig): ImageLoaderConfigBuilder {
    imageBitmapCacheSize = config.imageBitmapCache.maxSizeEntries
    imageVectorCacheSize = config.imageVectorCache.maxSizeEntries
    svgCacheSize = config.svgCache.maxSizeEntries
    config.fetchers.forEach { fetcher(it) }
    config.decoders.forEach { decoder(it) }
    config.mappers.values.flatten().forEach { mapper(it) }

    return this
}