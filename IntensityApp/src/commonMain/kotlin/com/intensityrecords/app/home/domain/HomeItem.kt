package com.intensityrecords.app.home.domain

import intensityrecordapp.intensityapp.generated.resources.Res
import intensityrecordapp.intensityapp.generated.resources._2
import intensityrecordapp.intensityapp.generated.resources._3
import intensityrecordapp.intensityapp.generated.resources._4
import intensityrecordapp.intensityapp.generated.resources._5
import org.jetbrains.compose.resources.DrawableResource


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

data class UiConfig(
    val id: Int,
    val name: String,
    val urlName: String,
    val blocks: List<UiBlock>,
    val design: String
)

data class UiBlock(
    val id: Int,
    val title: String,
    val imageUrl: String?,
    val order: Int,
    val url: String
)
