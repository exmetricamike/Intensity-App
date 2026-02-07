package com.intensityrecord.sensor.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AppParameters(
    @SerialName("polling_sec")
    val pollingSecs: Long = DEFAULT_APP_POLLING_INTERVAL_SECS,
    @SerialName("service_polling_sec")
    val servicePollingSecs: Long = DEFAULT_SERVICE_POLLING_INTERVAL_SECS,
    @SerialName("disconnect_service_sec")
    val disconnectServiceSecs: Long = DEFAULT_DISCONNECTION_TIMEOUT_SECS,
    @SerialName("snooze_sec")
    val snoozeSecs: Long = DEFAULT_SNOOZE_SECS,
    @SerialName("telemetry_sec")
    val telemetrySecs: Long = DEFAULT_SERVICE_TELEMETRY_INTERVAL_SECS
) {
    companion object {
        const val DEFAULT_APP_POLLING_INTERVAL_SECS = 5L
        const val DEFAULT_SERVICE_POLLING_INTERVAL_SECS = 20L
        const val DEFAULT_DISCONNECTION_TIMEOUT_SECS = 120L
        const val DEFAULT_SNOOZE_SECS = 300L // 5 * 60
        const val DEFAULT_SERVICE_TELEMETRY_INTERVAL_SECS = 900L // 15 * 60
    }
}
