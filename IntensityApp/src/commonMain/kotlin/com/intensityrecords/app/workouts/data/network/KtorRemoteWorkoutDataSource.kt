package com.intensityrecords.app.workouts.data.network

import com.intensityrecords.app.core.data.GlobalConfig
import com.intensityrecords.app.core.data.safeCall
import com.intensityrecords.app.core.domain.DataError
import com.intensityrecords.app.core.domain.Result
import com.intensityrecords.app.home.data.dto.HomeDto
import com.intensityrecords.app.workouts.data.dto.CollectionDetailDto
import com.intensityrecords.app.workouts.data.dto.WorkoutResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.headers

class KtorRemoteWorkoutDataSource(
    private val client: HttpClient
) : RemoteWorkoutDataSource {

    override suspend fun getWorkouts(id: String): Result<List<WorkoutResponseDto>, DataError.Remote> {
        val result = safeCall<List<WorkoutResponseDto>> {
            client.get(
                "${GlobalConfig.API_ENDPOINT}api/${GlobalConfig.API_VERSION}/hotel/workout?id=$id"
            )
        }
        if (GlobalConfig.DEBUG_PRINT_API_RESPONSE) println("APP_API Response: $result")
        return result
    }

    override suspend fun getWorkoutsCollection(
        hotelId: String,
        collectionId: Int
    ): Result<CollectionDetailDto, DataError.Remote> {
        val result = safeCall<CollectionDetailDto> {
            client.get(
                "${GlobalConfig.API_ENDPOINT}api/${GlobalConfig.API_VERSION}/hotel/$hotelId/collection/$collectionId"
            )
        }
        if (GlobalConfig.DEBUG_PRINT_API_RESPONSE) println("APP_API Response: $result")
        return result
    }

}