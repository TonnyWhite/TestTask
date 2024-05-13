package decoder


import config.ResourceConfig
import io.ktor.utils.io.ByteReadChannel
import kotlin.reflect.KClass

/**
 * Decodes [ByteReadChannel] to [T].
 */
interface Decoder<T : Any> {

    /**
     * The KClass of the output of this decoder
     */
    val outputKClass: KClass<T>

    /**
     * Decodes [channel] to [T].
     */
     suspend fun decode(channel: ByteReadChannel, resourceConfig: ResourceConfig): T
}
