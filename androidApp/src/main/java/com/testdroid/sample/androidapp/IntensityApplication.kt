package com.testdroid.sample.androidapp

import android.app.Application
import coil3.EventListener
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import coil3.decode.BitmapFactoryDecoder
import coil3.network.ktor3.KtorNetworkFetcherFactory
import coil3.request.ErrorResult
import coil3.request.ImageRequest
import com.intensityrecords.app.di.initKoin
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import java.util.concurrent.TimeUnit

class IntensityApplication : Application(), SingletonImageLoader.Factory {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@IntensityApplication)
        }
    }

    private val loggedImageErrors = mutableSetOf<String>()

    override fun newImageLoader(context: PlatformContext): ImageLoader {
        // A dedicated OkHttpClient for Coil with connection pooling disabled.
        // Without this, OkHttp drains the response body on the cancellation thread
        // (main thread) when a Coil request is cancelled during recomposition,
        // which throws NetworkOnMainThreadException.
        val coilOkHttpClient = OkHttpClient.Builder()
            .connectionPool(ConnectionPool(0, 1, TimeUnit.NANOSECONDS))
            .build()

        return ImageLoader.Builder(context)
            .components {
                add(KtorNetworkFetcherFactory(httpClient = {
                    HttpClient(OkHttp) {
                        engine {
                            preconfigured = coilOkHttpClient
                        }
                        install(HttpTimeout) {
                            requestTimeoutMillis = 15_000
                            connectTimeoutMillis = 10_000
                            socketTimeoutMillis = 15_000
                        }
                    }
                }))
                add(BitmapFactoryDecoder.Factory())
            }
            .eventListener(object : EventListener() {
                override fun onError(request: ImageRequest, result: ErrorResult) {
                    val key = request.data.toString()
                    if (loggedImageErrors.add(key)) {
                        println("COIL: Failed $key — ${result.throwable}")
                    }
                }
            })
            .build()
    }
}