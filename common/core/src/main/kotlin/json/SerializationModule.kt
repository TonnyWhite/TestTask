package json

import kotlinx.serialization.json.Json
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton

internal val serializationModule = DI.Module("serilizationModule"){
    bind<Json>() with singleton {
        Json{
            isLenient = true
            ignoreUnknownKeys = true

        }

    }
}