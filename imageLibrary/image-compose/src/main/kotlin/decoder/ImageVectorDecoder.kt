package decoder

import androidx.compose.ui.graphics.vector.ImageVector
import config.ResourceConfig
import loadXmlImageVector
import io.ktor.util.toByteArray
import io.ktor.utils.io.ByteReadChannel
import kotlin.reflect.KClass


/**
 * Decodes and transfers [ByteReadChannel] to [ImageVector].
 */
object ImageVectorDecoder : Decoder<ImageVector> {
    override val outputKClass: KClass<ImageVector> = ImageVector::class

    override suspend fun decode(channel: ByteReadChannel, resourceConfig: ResourceConfig): ImageVector {
        val xml = channel.toByteArray().decodeToString()
        return loadXmlImageVector(xml, resourceConfig.density)
    }
}