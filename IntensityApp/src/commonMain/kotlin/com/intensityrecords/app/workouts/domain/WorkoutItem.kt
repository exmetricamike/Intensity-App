package com.intensityrecords.app.workouts.domain

import intensityrecordapp.intensityapp.generated.resources.Res
import intensityrecordapp.intensityapp.generated.resources._1
import intensityrecordapp.intensityapp.generated.resources._2
import intensityrecordapp.intensityapp.generated.resources._3
import intensityrecordapp.intensityapp.generated.resources._4
import intensityrecordapp.intensityapp.generated.resources._5
import org.jetbrains.compose.resources.DrawableResource

data class WorkoutItem(
    val id: String,
    val title: String,
    val duration: String,
    val level: String,
    val imageUrl: String
)

//data class WorkoutItem(
//    val title: String,
//    val duration: String,
//    val level: String, // e.g., "MEDIUM", "HIGH"
//    val image: DrawableResource
//)

val workoutCategories = listOf(
    WorkoutItem("LEGS & BOOTY", "15-20 MIN", "MEDIUM", "Res.drawable._1","MEDIUM"),
    WorkoutItem("UPPER BODY", "15-20 MIN", "MEDIUM", "Res.drawable._2","MEDIUM"),
    WorkoutItem("FULL BODY", "20 MIN", "HIGH", "Res.drawable._3","MEDIUM"),
    WorkoutItem("ABS", "15 MIN", "MEDIUM", "Res.drawable._4","MEDIUM"),
    WorkoutItem("TABATA", "20 MIN", "HIGH", "Res.drawable._5","MEDIUM"),
    WorkoutItem("CARDIO", "20 MIN", "HIGH", "Res.drawable._1","MEDIUM")
)

data class Session(
    val id: Int,
    val title: String,
    val duration: String,
    val level: String,
    val image: DrawableResource
)


data class WorkoutSection(
    val id: Int,
    val title: String,
    val coverImage: String?,
    val collections: List<WorkoutCollection>
)

data class WorkoutCollection(
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

data class CollectionDetail(
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
    val videos: List<WorkoutVideo>
)

private fun localized(en: String?, fr: String?, nl: String?, locale: String): String? =
    (when (locale) { "fr" -> fr; "nl" -> nl; else -> en })
        ?.takeIf { it.isNotBlank() }
        ?: en

fun WorkoutCollection.title(locale: String): String? = localized(titleEn, titleFr, titleNl, locale)
fun WorkoutCollection.tagline(locale: String): String? = localized(taglineEn, taglineFr, taglineNl, locale)
fun CollectionDetail.title(locale: String): String? = localized(titleEn, titleFr, titleNl, locale)
fun CollectionDetail.tagline(locale: String): String? = localized(taglineEn, taglineFr, taglineNl, locale)

data class WorkoutVideo(
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
