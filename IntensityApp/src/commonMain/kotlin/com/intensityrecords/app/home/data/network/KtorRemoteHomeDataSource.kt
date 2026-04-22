package com.intensityrecords.app.home.data.network

import com.intensityrecords.app.core.data.GlobalConfig
import com.intensityrecords.app.core.data.SessionProvider
import com.intensityrecords.app.core.data.safeCall
import com.intensityrecords.app.core.domain.DataError
import com.intensityrecords.app.core.domain.Result
import com.intensityrecords.app.home.data.dto.DailyVideoDto
import com.intensityrecords.app.home.data.dto.HomeDto
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.headers

class KtorRemoteHomeDataSource(
    private val client: HttpClient,
    private val sessionProvider: SessionProvider
) : RemoteHomeDataSource {

    override suspend fun getHome(id: String): Result<HomeDto, DataError.Remote> {
        val result = safeCall<HomeDto> {
            client.get(
                urlString = "${GlobalConfig.API_ENDPOINT}api/${GlobalConfig.API_VERSION}/hotel/home?id=$id"
            )
        }
        if (GlobalConfig.DEBUG_PRINT_API_RESPONSE) println("APP_API Home Response: $result")
        return result
    }

    override suspend fun getDailyVideo(hotelId: String): Result<DailyVideoDto, DataError.Remote> {
        val result = safeCall<DailyVideoDto> {
            client.get(
                urlString = "${GlobalConfig.API_ENDPOINT}api/${GlobalConfig.API_VERSION}/hotel/$hotelId/dailyvideo/"
            )
        }
        if (GlobalConfig.DEBUG_PRINT_API_RESPONSE) println("APP_API DailyVideo Response: $result")
        return result
    }
}