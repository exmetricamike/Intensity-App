package intensityrecordapp.intensityapp.generateds.app.home.domain

import intensityrecordapp.intensityapp.generated.resources.Res
import intensityrecordapp.intensityapp.generated.resources._2
import intensityrecordapp.intensityapp.generated.resources._3
import intensityrecordapp.intensityapp.generated.resources._4
import intensityrecordapp.intensityapp.generated.resources._5
import org.jetbrains.compose.resources.DrawableResource


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