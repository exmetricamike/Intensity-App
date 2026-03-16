package com.intensityrecords.app.workouts.data.network

import com.intensityrecords.app.core.data.SessionProvider
import com.intensityrecords.app.core.data.safeCall
import com.intensityrecords.app.core.domain.DataError
import com.intensityrecords.app.core.domain.Result
import com.intensityrecords.app.workouts.data.dto.CollectionDetailDto
import com.intensityrecords.app.workouts.data.dto.WorkoutResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.headers

class KtorRemoteWorkoutDataSource(
    private val client: HttpClient,
    private val sessionProvider: SessionProvider
) : RemoteWorkoutDataSource {

    override suspend fun getWorkouts(id: String): Result<List<WorkoutResponseDto>, DataError.Remote> {
        return safeCall {
            client.get(
                "https://intensityapi.exmetrica.be/api/hotel/workout?id=$id"
            ) {
                val token = sessionProvider.getToken()
                if (!token.isNullOrEmpty()) {
                    headers {
                        append("Authorization", "Token $token")
                    }
                }
            }
        }
    }

    override suspend fun getWorkoutsCollection(
        hotelId: String,
        collectionId: Int
    ): Result<CollectionDetailDto, DataError.Remote> {
        return safeCall {
            client.get(
                "https://intensityapi.exmetrica.be/api/hotel/$hotelId/collection/$collectionId"
            ) {
                val token = sessionProvider.getToken()
                if (!token.isNullOrEmpty()) {
                    headers {
                        append("Authorization", "Token $token")
                    }
                }
            }
        }
    }

}