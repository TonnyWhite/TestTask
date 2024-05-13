package config

import androidx.compose.ui.unit.Density
import io.ktor.client.request.*
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class ResourceConfigBuilder(parentScope: CoroutineContext) {

    /**
     * [HttpRequestBuilder] to configure the request for this resource.
     * @see ResourceConfig.requestData
     */
    private val requestBuilder: HttpRequestBuilder = HttpRequestBuilder()

    /**
     * CoroutineContext used while loading the resource. Always use IO dispatchers
     * @see ResourceConfig.coroutineContext
     */
    var coroutineContext: CoroutineContext = parentScope.plus(Dispatchers.IO)

    /**
     * Screen density.
     * @see ResourceConfig.density
     */
     var density: Density = Density(1F, 1F)

    /**
     * Executes a [block] that configures the [HttpRequestBuilder] associated with this request.
     */
     fun requestBuilder(block: HttpRequestBuilder.() -> Unit): HttpRequestBuilder =
        requestBuilder.apply(block)

    /**
     * Creates immutable [ResourceConfig].
     */
     fun build(): ResourceConfig = object : ResourceConfig {

        override val requestData: HttpRequestData = requestBuilder.build()

        override val coroutineContext: CoroutineContext =
            this@ResourceConfigBuilder.coroutineContext

        override val density: Density = this@ResourceConfigBuilder.density

    }

}

/**
 * Copies all the data from [builder] and uses it as base for [this].
 */
fun ResourceConfigBuilder.takeFrom(builder: ResourceConfigBuilder): ResourceConfigBuilder =
    takeFrom(builder.build())

/**
 * Copies all the data from [config] and uses it as base for [this].
 */
 fun ResourceConfigBuilder.takeFrom(config: ResourceConfig): ResourceConfigBuilder {
    coroutineContext = config.coroutineContext
    density = config.density
    requestBuilder { takeFrom(config.requestData) }
    return this
}