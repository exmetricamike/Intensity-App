package com.intensityrecords.app.steptrip.data.mappers

import com.intensityrecords.app.steptrip.data.dto.StepTripDto
import com.intensityrecords.app.steptrip.domain.StepTripItem

fun StepTripDto.toDomain(): StepTripItem = StepTripItem(
    id = id,
    titleEn = title_en,
    titleFr = title_fr,
    titleNl = title_nl,
    categoryEn = category_en,
    categoryFr = category_fr,
    categoryNl = category_nl,
    descriptionEn = description_en,
    descriptionFr = description_fr,
    descriptionNl = description_nl,
    coverImage = cover_image,
    durationMin = duration_min,
    distanceKm = distance_km,
    caloriesBurned = calories_burned,
    mapsUrl = maps_url,
    order = order
)
