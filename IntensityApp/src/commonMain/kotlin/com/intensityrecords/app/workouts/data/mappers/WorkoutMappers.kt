package com.intensityrecords.app.workouts.data.mappers

import com.intensityrecords.app.workouts.data.dto.*
import com.intensityrecords.app.workouts.domain.*

fun WorkoutResponseDto.toWorkoutSection(): WorkoutSection {
    return WorkoutSection(
        id = id,
        title = title,
        coverImage = coverImage,
        collections = collections.map { it.toWorkoutCollection() }
    )
}

fun CollectionDto.toWorkoutCollection(): WorkoutCollection {
    return WorkoutCollection(
        id = id,
        name = name,
        description = description,
        coverImage = coverImage,
        collectionType = collectionType
    )
}



fun CollectionDetailDto.toCollectionDetail(): CollectionDetail {
    return CollectionDetail(
        id = id,
        name = name,
        description = description,
        coverImage = coverImage,
        videos = videos.map { it.toWorkoutVideo() }
    )
}

fun VideoDto.toWorkoutVideo(): WorkoutVideo {
    return WorkoutVideo(
        id = id,
        title = title,
        description = description,
        coverImage = coverImage,
        muxAssetId = muxAssetId
    )
}
