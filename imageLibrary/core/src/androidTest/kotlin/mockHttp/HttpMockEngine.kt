package mockHttp

import io.ktor.client.engine.mock.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.utils.io.*


val HttpMockEngine = MockEngine { request ->
    when (request.url.encodedPath) {
        "/emptyImage.jpg" -> respond(ByteReadChannel.Empty)
        else -> respondError(HttpStatusCode.NotFound)
    }
}

const val TestStringUrl = "https://www.example.com"

