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
        name = name,
        description = description,
        coverImage = coverImage,
        collectionType = collectionType
    )
}

fun ProgramCollectionDetailDto.toProgramCollectionDetail(): ProgramCollectionDetail {
    return ProgramCollectionDetail(
        id = id,
        name = name,
        description = description,
        coverImage = coverImage,
        videos = videos.map { it.toProgramVideo() }
    )
}

fun ProgramVideoDto.toProgramVideo(): ProgramVideo {
    return ProgramVideo(
        id = id,
        title = title,
        description = description,
        coverImage = coverImage,
        muxAssetId = muxAssetId,
        durationLabelMin = durationLabelMin,
        caloriesBurnedLabel = caloriesBurnedLabel
    )
}
