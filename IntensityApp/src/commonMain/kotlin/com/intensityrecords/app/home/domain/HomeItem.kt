package com.intensityrecords.app.home.domain


//data class HomeItem(
//    val title: String,
//    val icon: DrawableResource,
//    val isLive: Boolean = false
//)
//
//val sampleItems = listOf(
//    HomeItem("Workout", Res.drawable._2),
//    HomeItem("Mobility", Res.drawable._3),
//    HomeItem("Live Class", Res.drawable._4, isLive = true),
//    HomeItem("Step Trip", Res.drawable._5)
//)

data class HotelTheme(
    val id: Int,
    val hotelName: String,
    val hotelUrlName: String,
    val hotelLogo: String?,
    val hotelTagline: String?,
    val showLogo: Boolean,
    val showHeader: Boolean,
    val showTagline: Boolean,
    val primaryColor: String?,
    val secondaryColor: String?,
    val headerTextColor: String?,
    val titleTextColor: String?,
    val textColor: String?
)

data class UiConfig(
    val id: Int,
    val name: String,
    val urlName: String,
    val blocks: List<UiBlock>,
    val design: String,
    val theme: HotelTheme?
)

data class UiBlock(
    val id: Int,
    val title: String,
    val imageUrl: String?,
    val order: Int,
    val url: String
)
