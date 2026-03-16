package com.intensityrecords.app.mobility.domain

import com.intensityrecords.app.core.domain.DataError
import com.intensityrecords.app.core.domain.Result

interface MobilityRepository {
    suspend fun getMobility(): Result<List<MobilityItems>, DataError.Remote>
}