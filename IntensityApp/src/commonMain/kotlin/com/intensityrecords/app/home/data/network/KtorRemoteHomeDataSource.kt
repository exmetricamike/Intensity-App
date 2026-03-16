package com.intensityrecords.app.home.data.network

import com.intensityrecords.app.core.data.SessionProvider
import com.intensityrecords.app.core.data.safeCall
import com.intensityrecords.app.core.domain.DataError
import com.intensityrecords.app.core.domain.Result
import com.intensityrecords.app.home.data.dto.HomeDto
import com.intensityrecords.app.mobility.data.dto.MobilityResponse
import com.intensityrecords.app.mobility.data.network.RemoteMobilityDataSource
import com.intensityrecords.app.steptrip.data.dto.StepTripResponse
import com.intensityrecords.app.steptrip.data.network.RemoteStepTripDataSource
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.headers

class KtorRemoteHomeDataSource(
    private val client: HttpClient,
    private val sessionProvider: SessionProvider
) : RemoteHomeDataSource {

    override suspend fun getHome(id: String): Result<HomeDto, DataError.Remote> {
//        return safeCall<HomeDto> {
//            client.get(
//                urlString = "https://intensityapi.exmetrica.be/api/hotel/home?id=$id"
//            )
//        }
        val token = sessionProvider.getToken()
        println("API TOKEN ::: $token")

        return safeCall<HomeDto> {
            client.get(
                urlString = "https://intensityapi.exmetrica.be/api/hotel/home?id=$id"
            ) {
                if (!token.isNullOrEmpty()) {
                    headers {
                        append("Authorization", "Token $token")
                    }
                }
            }
        }
    }
}