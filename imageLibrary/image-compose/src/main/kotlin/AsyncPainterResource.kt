import androidx.compose.runtime.*
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalDensity
import config.ResourceConfigBuilder
import io.ktor.http.Url

class PainterFailure(message: String = "Failed to return a Painter") : Error(message)


/**
 * Loads a [Painter] resource asynchronously.
 * @param data Can be anything such as [String], [Url] or a [File].
 * @param key That is used in [remember] during composition, usually it's just [data].
 * @param filterQuality That is used by [BitmapPainter].
 * @param block Configuration for [ResourceConfig].
 * @param onLoadingPainter A [Painter] that is used when the resource is in [AsynchronousResourceLoading.Loading] state.
 * Note that, supplying a [Painter] object here will take precedence over [LoadImage] or [ZimranImageBox]
 * [onLoading] parameter.
 * @param onFailurePainter A [Painter] that is used when the resource is in [AsynchronousResourceLoading.Failure] state.
 * Note that, supplying a [Painter] object here will take precedence over [LoadImage] or [ZimranImageBox]
 * [onFailure] parameter.
 * @return [AsynchronousResourceLoading] Which contains a [Painter] that can be used to display an image using [LoadImage] or [ZimranImageBox].
 * @see LocalImageLoaderConfig
 */

@Composable
 inline fun <I : Any> asyncPainterResource(
    data: I,
    key: Any? = data,
    filterQuality: FilterQuality = DrawScope.DefaultFilterQuality,
    noinline onLoadingPainter: @Composable (Float) -> Result<Painter> = { Result.failure(
        PainterFailure()
    ) },
    noinline onFailurePainter: @Composable (Throwable) -> Result<Painter> = { Result.failure(
        PainterFailure()
    ) },
    crossinline block: ResourceConfigBuilder.() -> Unit = {},
): AsynchronousResourceLoading<Painter> {

    val imageLoaderConfig = LocalImageLoaderConfig.current
    val density = LocalDensity.current
    val scope = rememberCoroutineScope()
    val resourceConfig = remember(key, density) {
        ResourceConfigBuilder(scope.coroutineContext)
            .apply { this.density = density }
            .apply(block)
            .build()
    }

    val cachedResource = remember(key) {
        when (getDataSourceEnding(data)) {
            "svg" -> imageLoaderConfig.loadCachedResourceOrNull(data, imageLoaderConfig.svgCache)
            "xml" -> imageLoaderConfig.loadCachedResourceOrNull(data, imageLoaderConfig.imageVectorCache)
            else -> imageLoaderConfig.loadCachedResourceOrNull(data, imageLoaderConfig.imageBitmapCache)
        }
    }

    val painterAsynchronousResourceLoading by remember(key, resourceConfig) {
        when (getDataSourceEnding(data)) {
            "svg" -> imageLoaderConfig.loadSvgResource(data, resourceConfig)
            "xml" -> imageLoaderConfig.loadImageVectorResource(data, resourceConfig)
            else -> imageLoaderConfig.loadImageBitmapResource(data, resourceConfig)
        }
    }.collectAsState(cachedResource ?: AsynchronousResourceLoading.Loading(0F), resourceConfig.coroutineContext)

    val painterAsynchronousResourceWithFallbacksLoading = when (painterAsynchronousResourceLoading) {
        is AsynchronousResourceLoading.Loading -> {
            val asynchronousResourceLoading = painterAsynchronousResourceLoading as AsynchronousResourceLoading.Loading
            onLoadingPainter(asynchronousResourceLoading.progress)
                .mapCatching { painter -> AsynchronousResourceLoading.Success(painter) }
                .getOrDefault(painterAsynchronousResourceLoading)
        }

        is AsynchronousResourceLoading.Success -> painterAsynchronousResourceLoading
        is AsynchronousResourceLoading.Failure -> {
            val asynchronousResourceLoading = painterAsynchronousResourceLoading as AsynchronousResourceLoading.Failure
            onFailurePainter(asynchronousResourceLoading.exception)
                .mapCatching { painter -> AsynchronousResourceLoading.Success(painter) }
                .getOrDefault(painterAsynchronousResourceLoading)
        }
    }

    return painterAsynchronousResourceWithFallbacksLoading.map { value ->
        when (value) {
            is ImageVector -> rememberVectorPainter(value)
            is ImageBitmap -> remember(value) {
                BitmapPainter(value, filterQuality = filterQuality)
            }
            else -> remember(value) { value as Painter }
        }
    }
}


/**
 * Loads a [Painter] resource asynchronously.
 * @param data Can be anything such as [String], [Url] or a [File].
 * @param key That is used in [remember] during composition, usually it's just [data].
 * @param filterQuality That is used by [BitmapPainter].
 * @param block Configuration for [ResourceConfig].
 * @return [AsynchronousResourceLoading] Which contains a [Painter] that can be used to display an image using [LoadImage] or [ZimranImageBox].
 * @see LocalImageLoaderConfig
 */

@Composable
inline fun asyncPainterResource(
    data: Any,
    key: Any? = data,
    filterQuality: FilterQuality = DrawScope.DefaultFilterQuality,
    crossinline block: ResourceConfigBuilder.() -> Unit = {},
): AsynchronousResourceLoading<Painter> = asyncPainterResource(
    data,
    key,
    filterQuality,
    onLoadingPainter = { Result.failure(PainterFailure()) },
    onFailurePainter = { Result.failure(PainterFailure()) },
    block
)


inline fun <I : Any> getDataSourceEnding(data: I): String {
    val dataPath = (data as? Url)?.encodedPath ?: runCatching {
        Url(data.toString()).encodedPath
    }.getOrNull()

    return (dataPath ?: data.toString()).substringAfterLast('.')
}