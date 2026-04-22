package com.intensityrecords.app.program.data.mappers

import com.intensityrecords.app.program.data.dto.*
import com.intensityrecords.app.program.domain.*

fun ProgramResponseDto.toProgramSection(): ProgramSection {
    return ProgramSection(
        id = id,
        title = title,
        coverImage = coverImage,
        collections = collections.map { it.toProgramCollection() }
    )
}

fun ProgramCollectionDto.toProgramCollection(): ProgramCollection {
    return ProgramCollection(
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

fun ProgramCollectionDetailDto.toProgramCollectionDetail(): ProgramCollectionDetail {
    return ProgramCollectionDetail(
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
        videos = videos.map { it.toProgramVideo() }
    )
}

fun ProgramVideoDto.toProgramVideo(): ProgramVideo {
    return ProgramVideo(
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
