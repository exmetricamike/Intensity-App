package com.intensityrecords.app.mobility.domain

import intensityrecordapp.intensityapp.generated.resources.Res
import intensityrecordapp.intensityapp.generated.resources._1
import intensityrecordapp.intensityapp.generated.resources._2
import intensityrecordapp.intensityapp.generated.resources._3
import intensityrecordapp.intensityapp.generated.resources._4
import org.jetbrains.compose.resources.DrawableResource

data class MobilityItem(
    val title: String,
    val image: DrawableResource
)

val mobilityCategories = listOf(
    MobilityItem("MEETING BOOST", Res.drawable._1),
    MobilityItem("STRETCHING", Res.drawable._2),
    MobilityItem("RELAX AND SLEEP", Res.drawable._3),
    MobilityItem("LISTEN AND RUN", Res.drawable._4)
)