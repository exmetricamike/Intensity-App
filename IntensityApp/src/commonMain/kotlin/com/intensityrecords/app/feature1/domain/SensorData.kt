package com.intensityrecord.sensor.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SensorData(
    @SerialName("display_name")
    val displayName: String = "",
    @SerialName("pad_id")
    val padId: String = "",
    @SerialName("pad_id_label")
    val padIdLabel: String = "",
    @SerialName("latest")
    val latest: String? = null,
    @SerialName("min_since_latest_above_th")
    val minSinceLatestAboveTh: Int = 0,
    @SerialName("min_since_latest_below_threshold")
    val minSinceLatestBelowThreshold: Int = 0,
    @SerialName("status")
    val status: String = "",
    @SerialName("alert_triggered")
    val alertTriggered: Boolean = false,
    @SerialName("monitor_on")
    val monitorOn: Boolean = false,
    @SerialName("time_slot_1")
    val timeSlot1: Boolean = false,
    @SerialName("time_slot_2")
    val timeSlot2: Boolean = false,
    @SerialName("time_slot_3")
    val timeSlot3: Boolean = false,
    @SerialName("time_slot_4")
    val timeSlot4: Boolean = false,
    @SerialName("time_slot_5")
    val timeSlot5: Boolean = false,
    @SerialName("alert_time_idx")
    val alertTimeIdx: Int = 0,
    @SerialName("alert-time-labels")
    val alertTimeLabels: List<String> = emptyList(),
    @SerialName("repeat_alarm_until_seen")
    val repeatAlarmUntilSeen: Boolean = false
) {
    fun getElapsedTimeMinutes(): Int {
        return when (status) {
            "occupied" -> minSinceLatestBelowThreshold
            else -> minSinceLatestAboveTh
        }
    }

    fun formatElapsedTime(): String {
        if (status == "undetected") return "Undetected"
        if (status == "standby") return "Standby"

        val x = getElapsedTimeMinutes()
        if (x == 999999) return "--"

        val days = x / (24 * 60)
        if (days >= 1) {
            val prefix = if (status == "occupied") "In " else "Out "
            return "$prefix${days}d."
        }
        val hours = x / 60
        val minutes = x % 60
        val prefix = if (status == "occupied") "In " else "Out "
        return if (hours >= 1) {
            "$prefix${hours}h ${minutes}m"
        } else {
            "$prefix${minutes}m"
        }
    }

    val isUndetected: Boolean get() = status == "undetected"
}
