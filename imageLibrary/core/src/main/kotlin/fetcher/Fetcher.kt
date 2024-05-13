package fetcher


import DataSource
import AsynchronousResourceLoading
import config.ResourceConfig
import io.ktor.utils.io.*
import kotlinx.coroutines.flow.Flow
import kotlin.reflect.KClass

/**
 * Fetches and transfers data into a [ByteReadChannel] asynchronously.
 */
 interface Fetcher<T : Any> {

    /**
     * The KClass type for which this fetcher supports as a data input
     */
     val inputDataKClass: KClass<T>

    /**
     * Source from where data has been loaded.
     */
     val source: DataSource

    /**
     * Whether fetching from [T] is supported or not.
     */
     val T.isSupported: Boolean

    /**
     * fetches data [T] asynchronously as [AsynchronousResourceLoading] holding a [ByteReadChannel].
     * @param data type of data to fetch.
     * @param resourceConfig configuration used while fetching the resource.
     */
     fun fetchRemote(data: T, resourceConfig: ResourceConfig): Flow<AsynchronousResourceLoading<ByteReadChannel>>
}