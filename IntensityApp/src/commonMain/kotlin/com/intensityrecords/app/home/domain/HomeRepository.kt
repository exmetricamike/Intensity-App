package com.intensityrecords.app.home.domain

import com.intensityrecords.app.core.domain.DataError
import com.intensityrecords.app.core.domain.Result
import com.intensityrecords.app.mobility.domain.MobilityItems

interface HomeRepository {
    suspend fun getHome(id: String): Result<UiConfig, DataError.Remote>
}