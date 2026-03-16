package com.intensityrecords.app.workouts.domain

import com.intensityrecords.app.core.domain.DataError
import com.intensityrecords.app.core.domain.Result

interface WorkoutRepository {

    suspend fun getWorkouts(id: String): Result<List<WorkoutSection>, DataError.Remote>

    suspend fun getWorkoutsCollection(
        hotelId: String,
        collectionId: Int
    ): Result<CollectionDetail, DataError.Remote>

}