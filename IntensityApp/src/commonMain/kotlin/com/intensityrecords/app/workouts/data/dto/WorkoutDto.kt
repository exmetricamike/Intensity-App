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
    val name: String,
    val description: String,

    @SerialName("cover_image")
    val coverImage: String?,

    @SerialName("collection_type")
    val collectionType: String,

    @SerialName("is_active")
    val isActive: Boolean
)




@Serializable
data class CollectionDetailDto(
    val id: Int,
    val name: String,
    val description: String,

    @SerialName("cover_image")
    val coverImage: String?,

    @SerialName("collection_type")
    val collectionType: String,

    @SerialName("is_active")
    val isActive: Boolean,

    val videos: List<VideoDto>
)

@Serializable
data class VideoDto(
    val id: Int,
    val title: String,
    val description: String,

    @SerialName("cover_image")
    val coverImage: String?,

    @SerialName("mux_asset_id")
    val muxAssetId: String?,

    @SerialName("duration_label_min")
    val durationLabelMin: String?,

    @SerialName("calories_burned_label")
    val caloriesBurnedLabel: String?,

    val tags: List<String>,

    @SerialName("created_at")
    val createdAt: String,

    @SerialName("updated_at")
    val updatedAt: String
)

annotation class WorkoutDto
