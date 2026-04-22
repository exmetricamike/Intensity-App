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
    val titleEn: String?,
    val titleFr: String?,
    val titleNl: String?,
    val imageUrl: String?,
    val order: Int,
    val url: String?
)

private fun localizedBlock(en: String?, fr: String?, nl: String?, locale: String): String? =
    (when (locale) { "fr" -> fr; "nl" -> nl; else -> en })
        ?.takeIf { it.isNotBlank() }
        ?: en

fun UiBlock.title(locale: String): String? = localizedBlock(titleEn, titleFr, titleNl, locale)

data class DailyVideo(
    val id: Int,
    val date: String,
    val titleEn: String?,
    val titleFr: String?,
    val titleNl: String?,
    val taglineEn: String?,
    val taglineFr: String?,
    val taglineNl: String?,
    val coverImage: String?,
    val muxAssetIdEn: String?,
    val muxAssetIdFr: String?,
    val muxAssetIdNl: String?,
    val durationLabelMin: Int?,
    val caloriesBurnedLabel: Int?,
    val focusLabelEn: String?,
    val focusLabelFr: String?,
    val focusLabelNl: String?,
    val tags: List<String>
)

private fun localized(en: String?, fr: String?, nl: String?, locale: String): String? =
    (when (locale) { "fr" -> fr; "nl" -> nl; else -> en })
        ?.takeIf { it.isNotBlank() }
        ?: en

fun DailyVideo.title(locale: String) = localized(titleEn, titleFr, titleNl, locale)
fun DailyVideo.tagline(locale: String) = localized(taglineEn, taglineFr, taglineNl, locale)
fun DailyVideo.focusLabel(locale: String) = localized(focusLabelEn, focusLabelFr, focusLabelNl, locale)
fun DailyVideo.muxId(locale: String) = localized(muxAssetIdEn, muxAssetIdFr, muxAssetIdNl, locale)

fun muxStreamUrl(assetId: String) = "https://stream.mux.com/$assetId.m3u8"
fun muxThumbnailUrl(assetId: String) = "https://image.mux.com/$assetId/thumbnail.jpg"
