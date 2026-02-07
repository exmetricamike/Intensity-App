package com.intensityrecord.core.domain

enum class SensorStatus(val value: String) {
    Occupied("occupied"),
    Standby("standby"),
    Undetected("undetected"),
    BeforeFirst("before-first"),
    BeforeSecond("before-second"),
    BeforeThird("before-third"),
    AfterThird("after-third");

    companion object {
        fun fromValue(value: String): SensorStatus? =
            entries.firstOrNull { it.value == value }
    }
}
