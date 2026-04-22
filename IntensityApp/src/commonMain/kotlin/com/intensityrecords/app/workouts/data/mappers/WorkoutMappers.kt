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
        titleEn = titleEn,
        titleFr = titleFr,
        titleNl = titleNl,
        taglineEn = taglineEn,
        taglineFr = taglineFr,
        taglineNl = taglineNl,
        coverImage = coverImage,
        durationLabelMin = durationLabelMin,
        caloriesBurnedLabel = caloriesBurnedLabel
    )
}

fun CollectionDetailDto.toCollectionDetail(): CollectionDetail {
    return CollectionDetail(
        id = id,
        titleEn = titleEn,
        titleFr = titleFr,
        titleNl = titleNl,
        taglineEn = taglineEn,
        taglineFr = taglineFr,
        taglineNl = taglineNl,
        coverImage = coverImage,
        durationLabelMin = durationLabelMin,
        caloriesBurnedLabel = caloriesBurnedLabel,
        videos = videos.map { it.toWorkoutVideo() }
    )
}

fun VideoDto.toWorkoutVideo(): WorkoutVideo {
    return WorkoutVideo(
        id = id,
        title = title,
        description = description,
        coverImage = coverImage,
        muxAssetIdEn = muxAssetIdEn,
        muxAssetIdFr = muxAssetIdFr,
        muxAssetIdNl = muxAssetIdNl,
        durationLabelMin = durationLabelMin?.let { "$it MIN" },
        caloriesBurnedLabel = caloriesBurnedLabel?.let { "$it KCAL" }
    )
}
