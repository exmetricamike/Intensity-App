package com.intensityrecords.app.steptrip.data.network

import com.intensityrecords.app.core.data.GlobalConfig
import com.intensityrecords.app.core.data.safeCall
import com.intensityrecords.app.core.domain.DataError
import com.intensityrecords.app.core.domain.Result
import com.intensityrecords.app.steptrip.data.dto.StepTripDto
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class KtorRemoteStepTripDataSource(
    private val client: HttpClient
) : RemoteStepTripDataSource {

    override suspend fun getStepTrips(hotelId: String): Result<List<StepTripDto>, DataError.Remote> {
        val result = safeCall<List<StepTripDto>> {
            client.get(
                urlString = "${GlobalConfig.API_ENDPOINT}api/${GlobalConfig.API_VERSION}/hotel/$hotelId/steptrip/"
            )
        }
        if (GlobalConfig.DEBUG_PRINT_API_RESPONSE) println("APP_API StepTrips Response: $result")
        return result
    }
}
