package com.intensityrecords.app.home.domain

import org.jetbrains.compose.resources.DrawableResource
import com.intensityrecord.resources.Res
import com.intensityrecord.resources._2
import com.intensityrecord.resources._3
import com.intensityrecord.resources._4
import com.intensityrecord.resources._5

data class HomeItem(
    val title: String,
    val icon: DrawableResource,
    val isLive: Boolean = false
)

val sampleItems = listOf(
    HomeItem("Workout", Res.drawable._2),
    HomeItem("Mobility", Res.drawable._3),
    HomeItem("Live Class", Res.drawable._4, isLive = true),
    HomeItem("Step Trip", Res.drawable._5)
)