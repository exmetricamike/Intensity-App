package com.intensityrecords.app.steptrip.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class StepTripDto(
    val id: Int,
    val title_en: String?,
    val title_fr: String?,
    val title_nl: String?,
    val category_en: String?,
    val category_fr: String?,
    val category_nl: String?,
    val description_en: String?,
    val description_fr: String?,
    val description_nl: String?,
    val cover_image: String?,
    val duration_min: Int?,
    val distance_km: String?,
    val calories_burned: Int?,
    val maps_url: String?,
    val start_latitude: String?,
    val start_longitude: String?,
    val order: Int
)
