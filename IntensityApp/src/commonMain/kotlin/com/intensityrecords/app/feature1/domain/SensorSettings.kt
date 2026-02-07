package com.intensityrecord.sensor.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SensorSettings(
    @SerialName("pad_id")
    val padId: String,
    @SerialName("display_name")
    val displayName: String = "",
    @SerialName("is_monitoring_active")
    val isMonitoringActive: Boolean = false,
    @SerialName("time_slot_1_on")
    val timeSlot1On: Boolean = false,
    @SerialName("time_slot_2_on")
    val timeSlot2On: Boolean = false,
    @SerialName("time_slot_3_on")
    val timeSlot3On: Boolean = false,
    @SerialName("time_slot_4_on")
    val timeSlot4On: Boolean = false,
    @SerialName("time_slot_5_on")
    val timeSlot5On: Boolean = false,
    @SerialName("alert_time_idx")
    val alertTimeIdx: Int = 0,
    @SerialName("repeat_alarm_until_seen")
    val repeatAlarmUntilSeen: Boolean = false,
    @SerialName("device_id")
    val deviceId: String = ""
)
