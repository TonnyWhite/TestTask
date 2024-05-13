package image.fetcher

import DataSource
import AsynchronousResourceLoading
import android.content.ContentResolver
import android.content.Context
import config.ResourceConfig
import fetcher.Fetcher

import io.ktor.http.*
import io.ktor.utils.io.*
import io.ktor.utils.io.jvm.javaio.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import utils.path
import kotlin.reflect.KClass


internal class ResourcesFetcher(private val context: Context) : Fetcher<Url> {

    override val inputDataKClass: KClass<Url> = Url::class

    override val source: DataSource = DataSource.DISK

    override val Url.isSupported: Boolean
        get() = protocol.name == ContentResolver.SCHEME_ANDROID_RESOURCE

    override fun fetchRemote(
        data: Url,
        resourceConfig: ResourceConfig
    ): Flow<AsynchronousResourceLoading<ByteReadChannel>> = flow {
        val resId = data.path
            .toIntOrNull() ?: throw IllegalArgumentException("Invalid resource id $data")

        val bytes = context.resources.openRawResource(resId)
            .toByteReadChannel()

        emit(AsynchronousResourceLoading.Success(bytes))
    }

}