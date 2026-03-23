package com.intensityrecords.app.program.data.network

import com.intensityrecords.app.core.domain.DataError
import com.intensityrecords.app.core.domain.Result
import com.intensityrecords.app.program.data.dto.ProgramCollectionDetailDto
import com.intensityrecords.app.program.data.dto.ProgramResponseDto

interface RemoteProgramDataSource {

    suspend fun getPrograms(id: String): Result<List<ProgramResponseDto>, DataError.Remote>

    suspend fun getProgramCollection(
        hotelId: String,
        collectionId: Int
    ): Result<ProgramCollectionDetailDto, DataError.Remote>

}
