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
    val name: String,
    val description: String,
    val coverImage: String?,
    val collectionType: String
)




data class CollectionDetail(
    val id: Int,
    val name: String,
    val description: String,
    val coverImage: String?,
    val videos: List<WorkoutVideo>
)

data class WorkoutVideo(
    val id: Int,
    val title: String,
    val description: String,
    val coverImage: String?,
    val muxAssetId: String?
)
