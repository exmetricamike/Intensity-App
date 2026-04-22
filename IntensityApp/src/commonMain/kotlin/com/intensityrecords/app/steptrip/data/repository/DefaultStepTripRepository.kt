package com.intensityrecords.app.steptrip.data.repository

import com.intensityrecords.app.core.domain.DataError
import com.intensityrecords.app.core.domain.Result
import com.intensityrecords.app.core.domain.map
import com.intensityrecords.app.steptrip.data.mappers.toDomain
import com.intensityrecords.app.steptrip.data.network.RemoteStepTripDataSource
import com.intensityrecords.app.steptrip.domain.StepTripItem
import com.intensityrecords.app.steptrip.domain.StepTripRepository

class DefaultStepTripRepository(
    private val remote: RemoteStepTripDataSource
) : StepTripRepository {

    override suspend fun getStepTrips(hotelId: String): Result<List<StepTripItem>, DataError.Remote> {
        return remote.getStepTrips(hotelId).map { list ->
            list.map { it.toDomain() }
        }
    }
}
