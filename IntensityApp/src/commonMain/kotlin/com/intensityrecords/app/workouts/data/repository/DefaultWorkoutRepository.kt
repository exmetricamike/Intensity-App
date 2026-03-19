package com.intensityrecords.app.workouts.data.repository

import com.intensityrecords.app.core.domain.DataError
import com.intensityrecords.app.core.domain.Result
import com.intensityrecords.app.core.domain.map
import com.intensityrecords.app.workouts.data.mappers.toCollectionDetail
import com.intensityrecords.app.workouts.data.mappers.toWorkoutSection
import com.intensityrecords.app.workouts.data.network.RemoteWorkoutDataSource
import com.intensityrecords.app.workouts.domain.CollectionDetail
import com.intensityrecords.app.workouts.domain.WorkoutRepository
import com.intensityrecords.app.workouts.domain.WorkoutSection


class DefaultWorkoutRepository(
    private val remote: RemoteWorkoutDataSource
) : WorkoutRepository {

    override suspend fun getWorkouts(id: String): Result<List<WorkoutSection>, DataError.Remote> {
        return remote.getWorkouts(id).map { list ->
            list.map { it.toWorkoutSection() }
        }
    }

    override suspend fun getWorkoutsCollection(
        hotelId: String,
        collectionId: Int
    ): Result<CollectionDetail, DataError.Remote> {
        return remote.getWorkoutsCollection(
            hotelId,
            collectionId
        ).map {
            it.toCollectionDetail()
        }
    }


}