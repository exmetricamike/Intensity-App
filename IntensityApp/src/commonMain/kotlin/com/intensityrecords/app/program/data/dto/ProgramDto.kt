package com.intensityrecords.app.program.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProgramResponseDto(
    val id: Int,

    @SerialName("id_name")
    val idName: String,

    val title: String,

    @SerialName("cover_image")
    val coverImage: String?,

    val collections: List<ProgramCollectionDto>,

    @SerialName("is_active")
    val isActive: Boolean
)

@Serializable
data class ProgramCollectionDto(
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
data class ProgramCollectionDetailDto(
    val id: Int,
    val name: String,
    val description: String,

    @SerialName("cover_image")
    val coverImage: String?,

    @SerialName("collection_type")
    val collectionType: String,

    @SerialName("is_active")
    val isActive: Boolean,

    val videos: List<ProgramVideoDto>
)

@Serializable
data class ProgramVideoDto(
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
