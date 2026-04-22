package com.intensityrecords.app.home.data.network

import com.intensityrecords.app.core.domain.DataError
import com.intensityrecords.app.core.domain.Result
import com.intensityrecords.app.home.data.dto.DailyVideoDto
import com.intensityrecords.app.home.data.dto.HomeDto

interface RemoteHomeDataSource {
    suspend fun getHome(id: String): Result<HomeDto, DataError.Remote>
    suspend fun getDailyVideo(hotelId: String): Result<DailyVideoDto, DataError.Remote>
}