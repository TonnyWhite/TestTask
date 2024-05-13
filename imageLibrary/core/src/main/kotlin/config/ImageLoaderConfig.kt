package config

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import cache.Cache
import decoder.Decoder
import fetcher.Fetcher
import mapper.Mapper

import kotlin.reflect.KClass

const val DefaultCacheSize: Int = 100
const val DefaultHttpCacheSize: Long = 10 * 1024 * 1024

/**
 * Represents global configuration for ImageLoading.
 * @see ImageLoaderConfig to configure one.
 */
interface ImageLoaderConfig {

    val fetchers: List<Fetcher<Any>>

    val decoders: List<Decoder<Any>>

    val mappers: Map<KClass<*>, List<Mapper<Any, Any>>>

    /**
     * Number of entries to cache. Default is 100.
     */
    val imageBitmapCache: Cache<Any, ImageBitmap>

    val imageVectorCache: Cache<Any, ImageVector>

    val svgCache: Cache<Any, Painter>

     companion object
}

/**
 * Configures [ImageLoaderConfig] using [ImageLoaderConfigBuilder].
 */
inline fun ImageLoaderConfig(block: ImageLoaderConfigBuilder.() -> Unit): ImageLoaderConfig =
    ImageLoaderConfigBuilder().apply(block).build()