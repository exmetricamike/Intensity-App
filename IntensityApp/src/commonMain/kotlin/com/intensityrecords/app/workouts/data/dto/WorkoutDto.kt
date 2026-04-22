package com.intensityrecords.app.workouts.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WorkoutResponseDto(
    val id: Int,

    @SerialName("id_name")
    val idName: String,

    val title: String,

    @SerialName("cover_image")
    val coverImage: String?,

    val collections: List<CollectionDto>,

    @SerialName("is_active")
    val isActive: Boolean
)

@Serializable
data class CollectionDto(
    val id: Int,
    @SerialName("title_en") val titleEn: String? = null,
    @SerialName("title_fr") val titleFr: String? = null,
    @SerialName("title_nl") val titleNl: String? = null,
    @SerialName("tagline_en") val taglineEn: String? = null,
    @SerialName("tagline_fr") val taglineFr: String? = null,
    @SerialName("tagline_nl") val taglineNl: String? = null,

    @SerialName("cover_image") val coverImage: String? = null,
    @SerialName("duration_label_min") val durationLabelMin: Int? = null,
    @SerialName("calories_burned_label") val caloriesBurnedLabel: Int? = null,
    @SerialName("is_active") val isActive: Boolean
)

@Serializable
data class CollectionDetailDto(
    val id: Int,
    @SerialName("title_en") val titleEn: String? = null,
    @SerialName("title_fr") val titleFr: String? = null,
    @SerialName("title_nl") val titleNl: String? = null,
    @SerialName("tagline_en") val taglineEn: String? = null,
    @SerialName("tagline_fr") val taglineFr: String? = null,
    @SerialName("tagline_nl") val taglineNl: String? = null,

    @SerialName("cover_image") val coverImage: String? = null,
    @SerialName("duration_label_min") val durationLabelMin: Int? = null,
    @SerialName("calories_burned_label") val caloriesBurnedLabel: Int? = null,
    @SerialName("is_active") val isActive: Boolean,

    val videos: List<VideoDto>
)

@Serializable
data class VideoDto(
    val id: Int,
    val title: String,
    val description: String,

    @SerialName("cover_image")
    val coverImage: String?,

    @SerialName("mux_asset_id_en")
    val muxAssetIdEn: String?,

    @SerialName("mux_asset_id_fr")
    val muxAssetIdFr: String?,

    @SerialName("mux_asset_id_nl")
    val muxAssetIdNl: String?,

    @SerialName("duration_label_min")
    val durationLabelMin: Int?,

    @SerialName("calories_burned_label")
    val caloriesBurnedLabel: Int?,

    val tags: List<String>,

    @SerialName("created_at")
    val createdAt: String,

    @SerialName("updated_at")
    val updatedAt: String
)

annotation class WorkoutDto
