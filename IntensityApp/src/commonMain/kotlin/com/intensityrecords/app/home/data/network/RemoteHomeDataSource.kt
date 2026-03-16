package com.intensityrecords.app.home.data.network

import com.intensityrecords.app.core.domain.DataError
import com.intensityrecords.app.core.domain.Result
import com.intensityrecords.app.home.data.dto.HomeDto
import com.intensityrecords.app.mobility.data.dto.MobilityResponse
import com.intensityrecords.app.steptrip.data.dto.StepTripResponse


interface RemoteHomeDataSource {
    suspend fun getHome(id: String): Result<HomeDto, DataError.Remote>
}