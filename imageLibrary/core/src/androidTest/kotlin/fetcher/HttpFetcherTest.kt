package fetcher


import config.ResourceConfig
import config.ResourceConfigBuilder
import io.ktor.client.*
import io.ktor.http.*
import io.ktor.util.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import mockHttp.HttpMockEngine
import org.junit.Test

import kotlin.test.assertFalse
import kotlin.test.assertTrue

class HttpFetcherTest {

    private val fetcher: HttpFetcher = HttpFetcher(HttpClient(HttpMockEngine))

    @Test
    fun testWebSocketUrlIsSupported() = runTest {
        val urlBuilder = URLBuilder(protocol = URLProtocol.WS)
        val isSupported = with(fetcher) { Url(urlBuilder).isSupported }

        assertFalse { isSupported }
    }

    @Test
    fun testHttpUrlIsSupported() = runTest {
        val urlBuilder = URLBuilder(protocol = URLProtocol.HTTP)
        val isSupported = with(fetcher) { Url(urlBuilder).isSupported }

        assertTrue { isSupported }
    }

    @Test
    fun testHttpsUrlIsSupported() = runTest {
        val urlBuilder = URLBuilder(protocol = URLProtocol.HTTPS)
        val isSupported = with(fetcher) { Url(urlBuilder).isSupported }

        assertTrue { isSupported }
    }

}