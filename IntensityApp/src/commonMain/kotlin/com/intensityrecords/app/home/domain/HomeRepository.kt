package com.intensityrecords.app.home.domain

import com.intensityrecords.app.core.domain.DataError
import com.intensityrecords.app.core.domain.Result

interface HomeRepository {
    suspend fun getHome(id: String): Result<UiConfig, DataError.Remote>
    suspend fun getDailyVideo(hotelId: String): Result<DailyVideo, DataError.Remote>
}