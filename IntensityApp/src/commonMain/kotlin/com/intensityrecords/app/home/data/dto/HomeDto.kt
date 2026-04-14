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
    val uiBlockDesign: String,

    val theme: HotelThemeDto? = null
)

@Serializable
data class HotelThemeDto(
    val id: Int,

    @SerialName("hotel_name")
    val hotelName: String,

    @SerialName("hotel_url_name")
    val hotelUrlName: String,

    @SerialName("hotel_logo")
    val hotelLogo: String? = null,

    @SerialName("hotel_tagline")
    val hotelTagline: String? = null,

    @SerialName("show_logo")
    val showLogo: Boolean,

    @SerialName("show_header")
    val showHeader: Boolean,

    @SerialName("primary_color")
    val primaryColor: String? = null,

    @SerialName("secondary_color")
    val secondaryColor: String? = null,

    @SerialName("header_text_color")
    val headerTextColor: String? = null,

    @SerialName("title_text_color")
    val titleTextColor: String? = null,

    @SerialName("text_color")
    val textColor: String? = null
)

@Serializable
data class UiBlockDto(
    val id: Int,
    val title: String,
    val image: String? = null,
    val order: Int,
    val url: String
)

@Serializable
data class CollectionSetDto(
    val id: Int? = null
)
