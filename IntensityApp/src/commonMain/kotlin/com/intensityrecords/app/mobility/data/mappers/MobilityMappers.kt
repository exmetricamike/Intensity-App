package com.intensityrecords.app.mobility.data.mappers

import com.intensityrecords.app.mobility.data.dto.MobilityDto
import com.intensityrecords.app.mobility.domain.MobilityItems

fun MobilityDto.toDomain(): MobilityItems{
    return MobilityItems(
        id = id,
        title = title,
        duration = duration,
        image = image,
    )
}