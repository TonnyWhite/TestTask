import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import cache.Cache
import config.ImageLoaderConfig
import config.ResourceConfig
import kotlinx.coroutines.flow.*
import utils.findDecoderFor
import utils.findFetcherFor
import utils.mapInput
import kotlin.reflect.KClass

/**
 * Loads an [ImageBitmap]. This includes mapping, fetching, decoding and caching the image resource.
 * @see Fetcher
 * @see Decoder
 * @see Mapper
 * @see Cache
 */
fun <I : Any> ImageLoaderConfig.loadImageBitmapResource(
    data: I,
    resourceConfig: ResourceConfig,
    dataKClass: KClass<*> = data::class,
): Flow<AsynchronousResourceLoading<ImageBitmap>> = loadResource(data, dataKClass, resourceConfig, imageBitmapCache)

/**
 * Loads an [ImageVector]. This includes mapping, fetching, decoding and caching the image resource.
 * @see Fetcher
 * @see Decoder
 * @see Mapper
 * @see Cache
 */
 fun ImageLoaderConfig.loadImageVectorResource(
    data: Any,
    resourceConfig: ResourceConfig,
    dataKClass: KClass<*> = data::class
): Flow<AsynchronousResourceLoading<ImageVector>> = loadResource(data, dataKClass, resourceConfig, imageVectorCache)

/**
 * Loads SVG [Painter]. This includes mapping, fetching, decoding and caching the image resource.
 * @see Fetcher
 * @see Decoder
 * @see Mapper
 * @see Cache
 */
 fun ImageLoaderConfig.loadSvgResource(
    data: Any,
    resourceConfig: ResourceConfig,
    dataKClass: KClass<*> = data::class
): Flow<AsynchronousResourceLoading<Painter>> = loadResource(data, dataKClass, resourceConfig, svgCache)

private inline fun <reified T : Any> ImageLoaderConfig.loadResource(
    data: Any,
    dataKClass: KClass<*>,
    resourceConfig: ResourceConfig,
    cache: Cache<Any, T>,
): Flow<AsynchronousResourceLoading<T>> = flow {
    val output = mapInput(data, dataKClass)
    val cachedData = cache[output]
    if (cachedData != null) {
        val asynchronousResourceLoading = AsynchronousResourceLoading.Success(cachedData, DataSource.MEMORY_CACHE)
        emit(asynchronousResourceLoading)
    } else {
        val fetcher = findFetcherFor(output)
        val decoder = findDecoderFor<T>()
        val bytesFlow = fetcher.fetchRemote(output, resourceConfig)
        val dataFlow = bytesFlow.map { resource ->
            resource.map { channel ->
                decoder.decode(channel, resourceConfig).also {
                    cache[output] = it
                }
            }
        }
        emitAll(dataFlow)
    }
}.catch { emit(AsynchronousResourceLoading.Failure(it)) }

/**
 * Loads a cached [ImageBitmap], [ImageVector], or SVG [Painter] from memory. This includes mapping and loading the
 * cached image resource. If no resource has been cached for the provided data, `null` is returned.
 * @see Mapper
 * @see Cache
 */
 fun <T : Any> ImageLoaderConfig.loadCachedResourceOrNull(
    data: Any,
    cache: Cache<Any, T>,
    dataKClass: KClass<*> = data::class,
): AsynchronousResourceLoading<T>? {
    val output = mapInput(data, dataKClass)
    return cache[output]?.let { AsynchronousResourceLoading.Success(it, DataSource.MEMORY_CACHE) }
}
