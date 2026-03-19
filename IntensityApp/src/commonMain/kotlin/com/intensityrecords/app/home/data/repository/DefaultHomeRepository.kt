package com.intensityrecords.app.home.data.repository

import com.intensityrecords.app.core.domain.DataError
import com.intensityrecords.app.core.domain.Result
import com.intensityrecords.app.core.domain.map
import com.intensityrecords.app.home.data.mappers.toUiConfig
import com.intensityrecords.app.home.data.network.RemoteHomeDataSource
import com.intensityrecords.app.home.domain.HomeRepository
import com.intensityrecords.app.home.domain.UiConfig
import com.intensityrecords.app.mobility.data.mappers.toDomain
import com.intensityrecords.app.mobility.data.network.RemoteMobilityDataSource
import com.intensityrecords.app.mobility.domain.MobilityItems
import com.intensityrecords.app.mobility.domain.MobilityRepository


class DefaultHomeRepository(
    private val remote: RemoteHomeDataSource
) : HomeRepository {

    override suspend fun getHome(id: String): Result<UiConfig, DataError.Remote> {
        return remote.getHome(id = id).map {
            it.toUiConfig()
        }
    }

}