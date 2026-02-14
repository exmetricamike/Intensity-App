package com.intensityrecords.app.workouts.domain

import org.jetbrains.compose.resources.DrawableResource
import com.intensityrecord.resources.Res
import com.intensityrecord.resources._2
import com.intensityrecord.resources._1
import com.intensityrecord.resources._3
import com.intensityrecord.resources._4
import com.intensityrecord.resources._5

data class WorkoutItem(
    val title: String,
    val duration: String,
    val level: String, // e.g., "MEDIUM", "HIGH"
    val image: DrawableResource
)

val workoutCategories = listOf(
    WorkoutItem("LEGS & BOOTY", "15-20 MIN", "MEDIUM", Res.drawable._1),
    WorkoutItem("UPPER BODY", "15-20 MIN", "MEDIUM", Res.drawable._2),
    WorkoutItem("FULL BODY", "20 MIN", "HIGH", Res.drawable._3),
    WorkoutItem("ABS", "15 MIN", "MEDIUM", Res.drawable._4),
    WorkoutItem("TABATA", "20 MIN", "HIGH", Res.drawable._5),
    WorkoutItem("CARDIO", "20 MIN", "HIGH", Res.drawable._1)
)

data class Session(
    val id: Int,
    val title: String,
    val duration: String,
    val level: String,
    val image: DrawableResource
)