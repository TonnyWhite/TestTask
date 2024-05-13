package config


import decoder.ImageBitmapDecoder
import decoder.ImageVectorDecoder
import decoder.SvgDecoder
import io.ktor.client.engine.android.Android

val ImageLoaderConfig.Companion.Default: ImageLoaderConfig
    get() = ImageLoaderConfig {
        imageBitmapCacheSize = DefaultCacheSize
        imageVectorCacheSize = DefaultCacheSize
        svgCacheSize = DefaultCacheSize
        imageBitmapDecoder()
        imageVectorDecoder()
        svgDecoder()
        stringMapper()
        urlMapper()
        uriMapper()
        httpFetcher {
            httpCache(DefaultHttpCacheSize)
        }
        Android
    }



/**
 * Adds an [ImageBitmap] decoder to the [ImageLoaderConfigBuilder].
 */
 fun ImageLoaderConfigBuilder.imageBitmapDecoder(): Unit = decoder(ImageBitmapDecoder)

/**
 * Adds Decoder for XML Images to the [ImageLoaderConfigBuilder]
 */
 fun ImageLoaderConfigBuilder.imageVectorDecoder(): Unit = decoder(ImageVectorDecoder)

/**
 * Adds Decoder for SVG Images to the [ImageLoaderConfigBuilder]
 */
 fun ImageLoaderConfigBuilder.svgDecoder(): Unit = decoder(SvgDecoder)

