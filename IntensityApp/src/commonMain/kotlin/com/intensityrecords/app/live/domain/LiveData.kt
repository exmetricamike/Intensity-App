package com.intensityrecords.app.live.domain

// --- Models ---
data class ScheduleSlot(
    val time: String? = null,
    val title: String? = null,
    val isLive: Boolean = false
)

data class DaySchedule(
    val dayName: String,
    val slots: List<ScheduleSlot>,
    val isSelected: Boolean = false
)

// --- Mock Data ---
val mockSchedule = listOf(
    DaySchedule(
        "LUNDI", listOf(
            ScheduleSlot("09H00", "GOOD MORNING 15 MIN"),
            ScheduleSlot("12H30"),
            ScheduleSlot("18H00", "TABATA")
        )
    ),
    DaySchedule(
        "MARDI", listOf(
            ScheduleSlot(time = "09H00", title = "GOOD MORNING 15 MIN", isLive = true), // Live slot
            ScheduleSlot("09H00"),
            ScheduleSlot("18H00", "TABATA")
        ), isSelected = false
    ), // Highlighted Day
    DaySchedule(
        "MERCREDI", listOf(
            ScheduleSlot("09H00", "GOOD MORNING 15 MIN"),
            ScheduleSlot("12H30"),
            ScheduleSlot("20H00", "CARDIO")
        )
    ),
    DaySchedule(
        "JEUDI", listOf(
            ScheduleSlot("09H00", "TABATA"),
            ScheduleSlot("12H30"),
            ScheduleSlot("18H00", "CARDIO")
        )
    ),
    DaySchedule(
        "VENDREDI", listOf(
            ScheduleSlot(title = "GOOD MORNING 15 MIN"),
            ScheduleSlot("12H30"),
            ScheduleSlot("23H00", "CARDIO")
        )
    ),
    DaySchedule(
        "SAMEDI", listOf(
            ScheduleSlot("09H00", "TABATA"),
            ScheduleSlot("12H30"),
            ScheduleSlot("18H00", "CARDIO")
        )
    ),
    DaySchedule(
        "DIMANCHE", listOf(
            ScheduleSlot(title = "BOXING HIIT 50 MIN")
        )
    )
)
