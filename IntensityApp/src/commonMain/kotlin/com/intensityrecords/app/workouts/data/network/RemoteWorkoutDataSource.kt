package com.intensityrecords.app.workouts.data.network

import com.intensityrecords.app.core.domain.DataError
import com.intensityrecords.app.core.domain.Result
import com.intensityrecords.app.workouts.data.dto.CollectionDetailDto
import com.intensityrecords.app.workouts.data.dto.WorkoutResponseDto


interface RemoteWorkoutDataSource {

    suspend fun getWorkouts(id: String): Result<List<WorkoutResponseDto>, DataError.Remote>

    suspend fun getWorkoutsCollection(
        hotelId: String,
        collectionId: Int
    ): Result<CollectionDetailDto, DataError.Remote>

}