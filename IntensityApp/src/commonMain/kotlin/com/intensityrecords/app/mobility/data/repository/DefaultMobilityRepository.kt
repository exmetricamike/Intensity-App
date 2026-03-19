package com.intensityrecords.app.mobility.data.repository

import com.intensityrecords.app.core.domain.DataError
import com.intensityrecords.app.core.domain.Result
import com.intensityrecords.app.core.domain.map
import com.intensityrecords.app.mobility.data.mappers.toDomain
import com.intensityrecords.app.mobility.data.network.RemoteMobilityDataSource
import com.intensityrecords.app.mobility.domain.MobilityItems
import com.intensityrecords.app.mobility.domain.MobilityRepository


class DefaultMobilityRepository(
    private val remote: RemoteMobilityDataSource
) : MobilityRepository {

    override suspend fun getMobility(): Result<List<MobilityItems>, DataError.Remote> {
        return remote.getMobility().map { it ->
            it.mobility.map {
                it.toDomain()
            }
        }
    }

}