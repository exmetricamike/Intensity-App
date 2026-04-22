package com.intensityrecords.app.steptrip.data.network

import com.intensityrecords.app.core.domain.DataError
import com.intensityrecords.app.core.domain.Result
import com.intensityrecords.app.steptrip.data.dto.StepTripDto

interface RemoteStepTripDataSource {
    suspend fun getStepTrips(hotelId: String): Result<List<StepTripDto>, DataError.Remote>
}
