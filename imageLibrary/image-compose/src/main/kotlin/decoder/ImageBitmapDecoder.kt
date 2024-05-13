package decoder

import android.graphics.BitmapFactory

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import config.ResourceConfig
import io.ktor.util.toByteArray
import io.ktor.utils.io.ByteReadChannel
import kotlin.reflect.KClass

private const val Offset = 0

/**
 * Decodes and transfers [ByteReadChannel] to [ImageBitmap].
 */
object ImageBitmapDecoder : Decoder<ImageBitmap> {
    override val outputKClass: KClass<ImageBitmap> = ImageBitmap::class

    override suspend fun decode(channel: ByteReadChannel, resourceConfig: ResourceConfig): ImageBitmap {
        val bytes = channel.toByteArray()
        val bitmap = BitmapFactory.decodeByteArray(bytes, Offset, bytes.size)
            ?: throw IllegalArgumentException("Failed to decode ${bytes.size} bytes to a bitmap. Decoded bytes:\n${bytes.decodeToString()}\n")
        return bitmap.asImageBitmap()
    }
}