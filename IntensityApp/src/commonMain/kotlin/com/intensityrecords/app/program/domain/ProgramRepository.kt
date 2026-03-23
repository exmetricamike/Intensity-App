package com.intensityrecords.app.program.domain

import com.intensityrecords.app.core.domain.DataError
import com.intensityrecords.app.core.domain.Result

interface ProgramRepository {

    suspend fun getPrograms(id: String): Result<List<ProgramSection>, DataError.Remote>

    suspend fun getProgramCollection(
        hotelId: String,
        collectionId: Int
    ): Result<ProgramCollectionDetail, DataError.Remote>

}
