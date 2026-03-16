package com.intensityrecords.app.mobility.data.network

import com.intensityrecords.app.core.data.safeCall
import com.intensityrecords.app.core.domain.DataError
import com.intensityrecords.app.core.domain.Result
import com.intensityrecords.app.mobility.data.dto.MobilityResponse
import com.intensityrecords.app.steptrip.data.dto.StepTripResponse
import com.intensityrecords.app.steptrip.data.network.RemoteStepTripDataSource
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class KtorRemoteMobilityDataSource(
    private val client: HttpClient
) : RemoteMobilityDataSource {

    override suspend fun getMobility(): Result<MobilityResponse, DataError.Remote> {
        return safeCall<MobilityResponse> {
            client.get(
                urlString = "https://mobility.free.beeceptor.com/todos"
            )
        }
    }
}