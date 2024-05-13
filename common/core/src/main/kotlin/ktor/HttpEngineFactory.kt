package ktor

import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.android.Android

class HttpEngineFactory {
     fun createEngine() : HttpClientEngineFactory<HttpClientEngineConfig> = Android
}