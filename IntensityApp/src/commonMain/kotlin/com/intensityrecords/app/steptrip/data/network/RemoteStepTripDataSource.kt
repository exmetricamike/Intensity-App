package com.intensityrecords.app.steptrip.data.network

import com.intensityrecords.app.core.domain.DataError
import com.intensityrecords.app.core.domain.Result
import com.intensityrecords.app.steptrip.data.dto.StepTripResponse


interface RemoteStepTripDataSource {
    suspend fun getStepTrip(): Result<StepTripResponse, DataError.Remote>
}