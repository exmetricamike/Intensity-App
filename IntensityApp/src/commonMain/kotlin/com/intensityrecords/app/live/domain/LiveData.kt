package com.intensityrecords.app.live.domain

import intensityrecordapp.intensityapp.generated.resources.Res
import intensityrecordapp.intensityapp.generated.resources.monday
import intensityrecordapp.intensityapp.generated.resources.tuesday
import intensityrecordapp.intensityapp.generated.resources.wednesday
import intensityrecordapp.intensityapp.generated.resources.thursday
import intensityrecordapp.intensityapp.generated.resources.friday
import intensityrecordapp.intensityapp.generated.resources.saturday
import intensityrecordapp.intensityapp.generated.resources.sunday
import org.jetbrains.compose.resources.StringResource

// --- Models ---
data class ScheduleSlot(
    val time: String? = null,
    val title: String? = null,
    val isLive: Boolean = false
)

data class DaySchedule(
    val dayName: StringResource,
    val slots: List<ScheduleSlot>,
    val isSelected: Boolean = false
)

// --- Mock Data ---
val mockSchedule = listOf(
    DaySchedule(
        Res.string.monday, listOf(
            ScheduleSlot("09H00", "GOOD MORNING 15 MIN"),
            ScheduleSlot("12H30"),
            ScheduleSlot("18H00", "TABATA")
        )
    ),
    DaySchedule(
        Res.string.tuesday, listOf(
            ScheduleSlot(time = "09H00", title = "GOOD MORNING 15 MIN", isLive = true), // Live slot
            ScheduleSlot("09H00"),
            ScheduleSlot("18H00", "TABATA")
        ), isSelected = false
    ), // Highlighted Day
    DaySchedule(
        Res.string.wednesday, listOf(
            ScheduleSlot("09H00", "GOOD MORNING 15 MIN"),
            ScheduleSlot("12H30"),
            ScheduleSlot("20H00", "CARDIO")
        )
    ),
    DaySchedule(
        Res.string.thursday, listOf(
            ScheduleSlot("09H00", "TABATA"),
            ScheduleSlot("12H30"),
            ScheduleSlot("18H00", "CARDIO")
        )
    ),
    DaySchedule(
        Res.string.friday, listOf(
            ScheduleSlot(title = "GOOD MORNING 15 MIN"),
            ScheduleSlot("12H30"),
            ScheduleSlot("23H00", "CARDIO")
        )
    ),
    DaySchedule(
        Res.string.saturday, listOf(
            ScheduleSlot("09H00", "TABATA"),
            ScheduleSlot("12H30"),
            ScheduleSlot("18H00", "CARDIO")
        )
    ),
    DaySchedule(
        Res.string.sunday, listOf(
            ScheduleSlot(title = "BOXING HIIT 50 MIN")
        )
    )
)
