package com.intensityrecords.app.program.domain

data class ProgramSection(
    val id: Int,
    val title: String,
    val coverImage: String?,
    val collections: List<ProgramCollection>
)

data class ProgramCollection(
    val id: Int,
    val titleEn: String?,
    val titleFr: String?,
    val titleNl: String?,
    val taglineEn: String?,
    val taglineFr: String?,
    val taglineNl: String?,
    val coverImage: String?,
    val durationLabelMin: Int?,
    val caloriesBurnedLabel: Int?
)

data class ProgramCollectionDetail(
    val id: Int,
    val titleEn: String?,
    val titleFr: String?,
    val titleNl: String?,
    val taglineEn: String?,
    val taglineFr: String?,
    val taglineNl: String?,
    val coverImage: String?,
    val durationLabelMin: Int?,
    val caloriesBurnedLabel: Int?,
    val videos: List<ProgramVideo>
)

private fun localized(en: String?, fr: String?, nl: String?, locale: String): String? =
    (when (locale) { "fr" -> fr; "nl" -> nl; else -> en })
        ?.takeIf { it.isNotBlank() }
        ?: en

fun ProgramCollection.title(locale: String): String? = localized(titleEn, titleFr, titleNl, locale)
fun ProgramCollection.tagline(locale: String): String? = localized(taglineEn, taglineFr, taglineNl, locale)
fun ProgramCollectionDetail.title(locale: String): String? = localized(titleEn, titleFr, titleNl, locale)
fun ProgramCollectionDetail.tagline(locale: String): String? = localized(taglineEn, taglineFr, taglineNl, locale)

data class ProgramVideo(
    val id: Int,
    val title: String,
    val description: String,
    val coverImage: String?,
    val muxAssetIdEn: String?,
    val muxAssetIdFr: String?,
    val muxAssetIdNl: String?,
    val durationLabelMin: String?,
    val caloriesBurnedLabel: String?
) {
    fun resolvedMuxAssetId(locale: String): String? = when (locale.take(2).lowercase()) {
        "fr" -> muxAssetIdFr ?: muxAssetIdEn
        "nl" -> muxAssetIdNl ?: muxAssetIdEn
        else -> muxAssetIdEn
    }
}
