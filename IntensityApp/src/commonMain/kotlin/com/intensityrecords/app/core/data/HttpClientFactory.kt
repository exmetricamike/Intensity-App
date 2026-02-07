package com.intensityrecord.core.data

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.accept
import io.ktor.client.request.headers
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json

object HttpClientFactory {

    fun create(engine: HttpClientEngine, tokenProvider: suspend () -> String?): HttpClient {
        return HttpClient(engine) {

            expectSuccess = false

            install(ContentNegotiation) {
                json(
                    json = Json {
                        ignoreUnknownKeys = true
                    }
                )
            }

            install(HttpTimeout) {
                socketTimeoutMillis = 20_000L
                requestTimeoutMillis = 20_000L
            }

            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        println(message)
                    }
                }
                level = LogLevel.ALL
            }

            defaultRequest {
                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)
            }
        }.also { client ->
            // Inject Token authorization header via request pipeline
            client.requestPipeline.intercept(io.ktor.client.request.HttpRequestPipeline.State) {
                val token = tokenProvider()
                if (!token.isNullOrBlank()) {
                    context.headers.append(HttpHeaders.Authorization, "Token $token")
                }
            }
        }
    }
}
