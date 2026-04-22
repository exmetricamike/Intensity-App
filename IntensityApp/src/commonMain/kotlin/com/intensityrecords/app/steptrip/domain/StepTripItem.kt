package com.intensityrecords.app.steptrip.domain

data class StepTripItem(
    val id: Int,
    val titleEn: String?,
    val titleFr: String?,
    val titleNl: String?,
    val categoryEn: String?,
    val categoryFr: String?,
    val categoryNl: String?,
    val descriptionEn: String?,
    val descriptionFr: String?,
    val descriptionNl: String?,
    val coverImage: String?,
    val durationMin: Int?,
    val distanceKm: String?,
    val caloriesBurned: Int?,
    val mapsUrl: String?,
    val order: Int
)

private fun localized(en: String?, fr: String?, nl: String?, locale: String): String? =
    (when (locale) { "fr" -> fr; "nl" -> nl; else -> en })
        ?.takeIf { it.isNotBlank() } ?: en

fun StepTripItem.title(locale: String): String =
    localized(titleEn, titleFr, titleNl, locale) ?: titleEn ?: ""

fun StepTripItem.category(locale: String): String? =
    localized(categoryEn, categoryFr, categoryNl, locale)

fun StepTripItem.description(locale: String): String? =
    localized(descriptionEn, descriptionFr, descriptionNl, locale)
