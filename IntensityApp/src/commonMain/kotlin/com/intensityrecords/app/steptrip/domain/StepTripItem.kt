package com.intensityrecords.app.steptrip.domain

import com.intensityrecord.resources.Res
import com.intensityrecord.resources.trip_1
import com.intensityrecord.resources.trip_2
import com.intensityrecord.resources.trip_3
import org.jetbrains.compose.resources.DrawableResource

val trips = listOf(
    TripData(
        "WATERLOO BATTLEFIELD DOMAIN",
        "History",
        "58 min",
        "4.7 km",
        "280 kcal",
        Res.drawable.trip_1
    ),
    TripData("MUSÉE WELLINGTON", "History", "58 min", "4.7 km", "280 kcal", Res.drawable.trip_2),
    TripData("LION'S MOUND", "Nature", "45 min", "3.2 km", "210 kcal", Res.drawable.trip_3),
    TripData(
        "HOUGOUMONT FARM",
        "History",
        "40 min",
        "3.5 km",
        "230 kcal",
        Res.drawable.trip_1
    ),
    TripData(
        "LA HAYE SAINTE FARM",
        "History",
        "35 min",
        "2.8 km",
        "190 kcal",
        Res.drawable.trip_2
    ),
    TripData(
        "WAVRE BATTLE SITE",
        "History",
        "50 min",
        "4.1 km",
        "260 kcal",
        Res.drawable.trip_3
    ),
    TripData(
        "WATERLOO MEMORIAL PARK",
        "Nature",
        "30 min",
        "2.2 km",
        "170 kcal",
        Res.drawable.trip_1
    )
)

data class TripData(
    val title: String,
    val category: String,
    val duration: String,
    val distance: String,
    val calories: String,
    val image: DrawableResource = Res.drawable.trip_1
)
