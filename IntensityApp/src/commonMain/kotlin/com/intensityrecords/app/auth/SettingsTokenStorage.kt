package com.intensityrecord.auth
import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import com.russhwolf.settings.set
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SettingsTokenStorage(private val settings: Settings) : TokenStorage {
    private val KEY_ACCESS = "auth.access"
    private val KEY_REFRESH = "auth.refresh"

    override suspend fun saveTokens(accessToken: String?, refreshToken: String?) {
        // small suspension point to ensure non-blocking when used with e.g. android file IO backing
        withContext(Dispatchers.Default) {
            settings[KEY_ACCESS] = accessToken ?: ""
            settings[KEY_REFRESH] = refreshToken ?: ""
        }
    }


    override suspend fun clearTokens() {
        withContext(Dispatchers.Default) {
            settings[KEY_ACCESS] = ""
            settings[KEY_REFRESH] = ""
        }
    }

    override suspend fun getAccessToken(): String? {
        return withContext(Dispatchers.Default) {
            settings.getStringOrNull(KEY_ACCESS)
        }
    }

    override suspend fun getRefreshToken(): String? {
        return withContext(Dispatchers.Default) {
            settings.getStringOrNull(KEY_REFRESH)
        }
    }
}

// helper extension for Settings (if needed)
fun Settings.getStringOrNull(key: String): String? {
    val value = this[key, ""]
    return if (value.isBlank()) null else value
}
