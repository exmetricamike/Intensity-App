package com.intensityrecords.app.workouts.domain

import intensityrecordapp.intensityapp.generated.resources.Res
import intensityrecordapp.intensityapp.generated.resources._1
import intensityrecordapp.intensityapp.generated.resources._2
import intensityrecordapp.intensityapp.generated.resources._3
import intensityrecordapp.intensityapp.generated.resources._4
import intensityrecordapp.intensityapp.generated.resources._5
import org.jetbrains.compose.resources.DrawableResource


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