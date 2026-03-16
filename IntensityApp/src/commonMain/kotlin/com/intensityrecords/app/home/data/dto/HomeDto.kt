package com.intensityrecords.app.home.data.dto

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class HomeDto(
    val id: Int,
    val name: String,

    @SerialName("url_name")
    val urlName: String,

    @SerialName("collection_sets")
    val collectionSets: List<CollectionSetDto> = emptyList(),

    @SerialName("ui_blocks")
    val uiBlocks: List<UiBlockDto>,

    @SerialName("ui_block_design")
    val uiBlockDesign: String
)

@Serializable
data class UiBlockDto(
    val id: Int,
    val title: String,
    val image: String,
    val order: Int,
    val url: String
)

@Serializable
data class CollectionSetDto(
    val id: Int? = null
)
