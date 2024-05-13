@file:Suppress("UNCHECKED_CAST")

package utils

import config.ImageLoaderConfig
import decoder.Decoder
import fetcher.Fetcher
import kotlin.reflect.KClass

internal fun ImageLoaderConfig.mapInput(input: Any, inputKClass: KClass<*>): Any {

    val output = mappers[inputKClass]
        ?.lastOrNull { mapper -> with(mapper) { input.isSupported } }
        ?.map(input)

    return output ?: input
}

internal fun <T : Any> ImageLoaderConfig.findFetcherFor(data: T): Fetcher<T> {

    val type = data::class

    val fetcher = fetchers.findLast { fetcher ->

        val fetcherType = fetcher.inputDataKClass

        val isSameType = fetcherType == type

        isSameType && with(fetcher) { data.isSupported }
    }

    checkNotNull(fetcher) { "Unable to find a fetcher for $type" }

    return fetcher as Fetcher<T>
}

internal inline fun <reified T : Any> ImageLoaderConfig.findDecoderFor(): Decoder<T> {

    val type = T::class

    val decoder = decoders.findLast { decoder ->

        val decoderType = decoder.outputKClass

        decoderType == type
    }

    checkNotNull(decoder) { "Unable to find a decoder for $type" }

    return decoder as Decoder<T>
}
