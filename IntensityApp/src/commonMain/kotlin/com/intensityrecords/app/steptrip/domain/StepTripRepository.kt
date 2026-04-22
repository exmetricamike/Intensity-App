package com.intensityrecords.app.steptrip.domain

import com.intensityrecords.app.core.domain.DataError
import com.intensityrecords.app.core.domain.Result

interface StepTripRepository {
    suspend fun getStepTrips(hotelId: String): Result<List<StepTripItem>, DataError.Remote>
}
