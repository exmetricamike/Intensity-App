package com.intensityrecords.app.steptrip.data.mappers

import com.intensityrecords.app.steptrip.data.dto.StepTripDto
import com.intensityrecords.app.steptrip.domain.StepTripItem

fun StepTripDto.toDomain(): StepTripItem {
    return StepTripItem(
        id = id,
        title = title,
        duration = duration,
        category = category,
        distance = distance,
        calories = calories,
        image = image,
    )
}