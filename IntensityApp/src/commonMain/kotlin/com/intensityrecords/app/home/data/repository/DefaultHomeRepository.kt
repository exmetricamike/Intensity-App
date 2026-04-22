package com.intensityrecords.app.home.data.repository

import com.intensityrecords.app.core.domain.DataError
import com.intensityrecords.app.core.domain.Result
import com.intensityrecords.app.core.domain.map
import com.intensityrecords.app.home.data.mappers.toDailyVideo
import com.intensityrecords.app.home.data.mappers.toUiConfig
import com.intensityrecords.app.home.data.network.RemoteHomeDataSource
import com.intensityrecords.app.home.domain.DailyVideo
import com.intensityrecords.app.home.domain.HomeRepository
import com.intensityrecords.app.home.domain.UiConfig

class DefaultHomeRepository(
    private val remote: RemoteHomeDataSource
) : HomeRepository {

    override suspend fun getHome(id: String): Result<UiConfig, DataError.Remote> {
        return remote.getHome(id = id).map { it.toUiConfig() }
    }

    override suspend fun getDailyVideo(hotelId: String): Result<DailyVideo, DataError.Remote> {
        return remote.getDailyVideo(hotelId = hotelId).map { it.toDailyVideo() }
    }
}