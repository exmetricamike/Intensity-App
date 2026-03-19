package com.intensityrecords.app.mobility.data.network

import com.intensityrecords.app.core.domain.DataError
import com.intensityrecords.app.core.domain.Result
import com.intensityrecords.app.mobility.data.dto.MobilityResponse
import com.intensityrecords.app.steptrip.data.dto.StepTripResponse


interface RemoteMobilityDataSource {
    suspend fun getMobility(): Result<MobilityResponse, DataError.Remote>
}