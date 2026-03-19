package com.intensityrecords.app.steptrip.data.network

import com.intensityrecords.app.core.data.safeCall
import com.intensityrecords.app.core.domain.DataError
import com.intensityrecords.app.core.domain.Result
import com.intensityrecords.app.steptrip.data.dto.StepTripResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class KtorRemoteStepTripDataSource(
    private val client: HttpClient
) : RemoteStepTripDataSource {

    override suspend fun getStepTrip(): Result<StepTripResponse, DataError.Remote> {
        return safeCall<StepTripResponse> {
            client.get(
                urlString = "https://steptrips.free.beeceptor.com/todos"
            )
        }
    }
}