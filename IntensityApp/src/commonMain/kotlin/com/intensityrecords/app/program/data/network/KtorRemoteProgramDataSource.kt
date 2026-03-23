package com.intensityrecords.app.program.data.network

import com.intensityrecords.app.core.data.GlobalConfig
import com.intensityrecords.app.core.data.safeCall
import com.intensityrecords.app.core.domain.DataError
import com.intensityrecords.app.core.domain.Result
import com.intensityrecords.app.program.data.dto.ProgramCollectionDetailDto
import com.intensityrecords.app.program.data.dto.ProgramResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class KtorRemoteProgramDataSource(
    private val client: HttpClient
) : RemoteProgramDataSource {

    override suspend fun getPrograms(id: String): Result<List<ProgramResponseDto>, DataError.Remote> {
        val result = safeCall<List<ProgramResponseDto>> {
            client.get(
                "${GlobalConfig.API_ENDPOINT}api/${GlobalConfig.API_VERSION}/hotel/$id/program/"
            )
        }
        if (GlobalConfig.DEBUG_PRINT_API_RESPONSE) println("APP_API Response: $result")
        return result
    }

    override suspend fun getProgramCollection(
        hotelId: String,
        collectionId: Int
    ): Result<ProgramCollectionDetailDto, DataError.Remote> {
        val result = safeCall<ProgramCollectionDetailDto> {
            client.get(
                "${GlobalConfig.API_ENDPOINT}api/${GlobalConfig.API_VERSION}/hotel/$hotelId/collection/$collectionId"
            )
        }
        if (GlobalConfig.DEBUG_PRINT_API_RESPONSE) println("APP_API Response: $result")
        return result
    }

}
