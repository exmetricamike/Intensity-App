package com.intensityrecord.core.data

import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import com.russhwolf.settings.set
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ZensiPreferences(private val settings: Settings) {

    companion object {
        private const val KEY_TOKEN = "zensi.token"
        private const val KEY_USERNAME = "zensi.username"
        private const val KEY_PASSWORD = "zensi.password"
        private const val KEY_LOGO = "zensi.logo"
        private const val KEY_TIMESLOTS = "zensi.timeslots"
        private const val KEY_NUM_COLUMNS = "zensi.num_columns"
        private const val KEY_SOUND = "zensi.sound"
        private const val KEY_VIBRATE = "zensi.vibrate"
        private const val KEY_PIN = "zensi.pincode"
        private const val KEY_DEVICE_ID = "zensi.device_id"

        private const val DEFAULT_TIMESLOTS = """[["07:30","11:30"],["11:30","13:30"],["13:30","18:30"],["18:30","21:30"],["21:30","07:30"]]"""
    }

    // --- Token ---
    var token: String?
        get() = settings.getStringOrNull(KEY_TOKEN)
        set(value) { settings[KEY_TOKEN] = value ?: "" }

    // --- Credentials ---
    var username: String?
        get() = settings.getStringOrNull(KEY_USERNAME)
        set(value) { settings[KEY_USERNAME] = value ?: "" }

    var password: String?
        get() = settings.getStringOrNull(KEY_PASSWORD)
        set(value) { settings[KEY_PASSWORD] = value ?: "" }

    fun storeCredentials(username: String, password: String) {
        this.username = username
        this.password = password
    }

    // --- Logo ---
    var logoUrl: String?
        get() = settings.getStringOrNull(KEY_LOGO)
        set(value) { settings[KEY_LOGO] = value ?: "" }

    // --- Time Slots ---
    var timeslots: List<List<String>>
        get() {
            val json = settings[KEY_TIMESLOTS, DEFAULT_TIMESLOTS]
            return try {
                Json.decodeFromString(json)
            } catch (_: Exception) {
                Json.decodeFromString(DEFAULT_TIMESLOTS)
            }
        }
        set(value) {
            settings[KEY_TIMESLOTS] = Json.encodeToString(value)
        }

    // --- Display columns ---
    var numColumns: Int
        get() = settings[KEY_NUM_COLUMNS, 3]
        set(value) { settings[KEY_NUM_COLUMNS] = value }

    // --- Sound ---
    var soundEnabled: Boolean
        get() = settings[KEY_SOUND, true]
        set(value) { settings[KEY_SOUND] = value }

    // --- Vibrate ---
    var vibrateEnabled: Boolean
        get() = settings[KEY_VIBRATE, true]
        set(value) { settings[KEY_VIBRATE] = value }

    // --- Pin ---
    var userPin: String?
        get() = settings.getStringOrNull(KEY_PIN)
        set(value) { settings[KEY_PIN] = value ?: "" }

    // --- Device ID ---
    var deviceId: String
        get() = settings[KEY_DEVICE_ID, ""]
        set(value) { settings[KEY_DEVICE_ID] = value }

    // --- Snooze per sensor ---
    fun setSnooze(padId: String, expirationTimeMillis: Long) {
        settings["snooze_$padId"] = expirationTimeMillis
    }

    fun getSnoozeTime(padId: String): Long {
        return settings["snooze_$padId", 0L]
    }

    fun clearSnooze(padId: String) {
        settings.remove("snooze_$padId")
    }

    // --- Alarm state per sensor ---
    fun setInAlarm(padId: String, inAlarm: Boolean) {
        settings["inalarm_$padId"] = inAlarm
    }

    fun getInAlarm(padId: String): Boolean {
        return settings["inalarm_$padId", true]
    }

    // --- Clear all ---
    fun clear() {
        settings.clear()
    }

    fun clearAllExceptCredentials() {
        val savedUsername = username
        val savedPassword = password
        clear()
        if (savedUsername != null) username = savedUsername
        if (savedPassword != null) password = savedPassword
    }

    val isLoggedIn: Boolean
        get() = !token.isNullOrBlank()

    val isDemoMode: Boolean
        get() = username == "feature1_demo"

    private fun Settings.getStringOrNull(key: String): String? {
        val value = this[key, ""]
        return if (value.isBlank()) null else value
    }
}
