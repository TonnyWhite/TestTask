package mapper


import io.ktor.http.*
import org.junit.Test
import utils.createURI
import utils.createURL
import java.net.URI
import java.net.URL
import kotlin.test.assertEquals

class MappersTest {

    private val stringMapper: Mapper<String, Url> = StringMapper
    private val urlMapper: Mapper<URL, Url> = URLMapper
    private val uriMapper: Mapper<URI, Url> = URIMapper

    @Test
    fun testStringMapper() {
        val url = stringMapper.map("https://www.example.com")

        assertEquals(Url("https://www.example.com"), url)
    }

    @Test
    fun testURLMapper() {
        val url = urlMapper.map(createURL("https://www.example.com:443"))

        assertEquals(Url("https://www.example.com:443"), url)
    }

    @Test
    fun testURIMapper() {
        val url = uriMapper.map(createURI("https://www.example.com:443"))

        assertEquals(Url("https://www.example.com:443"), url)
    }

}