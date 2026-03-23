package com.intensityrecords.app.program.data.repository

import com.intensityrecords.app.core.domain.DataError
import com.intensityrecords.app.core.domain.Result
import com.intensityrecords.app.core.domain.map
import com.intensityrecords.app.program.data.mappers.toProgramCollectionDetail
import com.intensityrecords.app.program.data.mappers.toProgramSection
import com.intensityrecords.app.program.data.network.RemoteProgramDataSource
import com.intensityrecords.app.program.domain.ProgramCollectionDetail
import com.intensityrecords.app.program.domain.ProgramRepository
import com.intensityrecords.app.program.domain.ProgramSection

class DefaultProgramRepository(
    private val remote: RemoteProgramDataSource
) : ProgramRepository {

    override suspend fun getPrograms(id: String): Result<List<ProgramSection>, DataError.Remote> {
        return remote.getPrograms(id).map { list ->
            list.map { it.toProgramSection() }
        }
    }

    override suspend fun getProgramCollection(
        hotelId: String,
        collectionId: Int
    ): Result<ProgramCollectionDetail, DataError.Remote> {
        return remote.getProgramCollection(hotelId, collectionId).map {
            it.toProgramCollectionDetail()
        }
    }

}
