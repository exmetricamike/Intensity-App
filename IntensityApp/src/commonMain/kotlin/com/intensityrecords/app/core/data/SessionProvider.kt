package com.intensityrecords.app.core.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

class SessionProvider(private val dataStore: DataStore<Preferences>) {
    private val TOKEN_KEY = stringPreferencesKey("auth_token")
    private val TOKEN_ID = stringPreferencesKey("auth_id")

    //    val authToken: Flow<String?> = dataStore.data.map { it[TOKEN_KEY] }
//    val authId: Flow<String?> = dataStore.data.map { it[TOKEN_ID] }

    private var cachedToken: String? = null

    private var cachedAuthId: String? = null


    val authToken: Flow<String?> =
        dataStore.data.map { prefs ->
            val token = prefs[TOKEN_KEY]
            cachedToken = token
            token
        }

    val authId: Flow<String?> =
        dataStore.data.map { prefs ->
            val id = prefs[TOKEN_ID]
            cachedAuthId = id
            id
        }

    fun getToken(): String? {
        return cachedToken
    }

    fun getAuthId(): String? {
        return cachedAuthId
    }


    val authState: Flow<AuthState> =
        authToken.map { token ->
            if (token.isNullOrEmpty()) {
                AuthState.LoggedOut
            } else {
                AuthState.LoggedIn(token)
            }
        }.onStart {
            emit(AuthState.Loading)
        }


    suspend fun saveToken(token: String, id: String) {
        cachedToken = token
        cachedAuthId = id

        dataStore.edit { it[TOKEN_KEY] = token }
        dataStore.edit { it[TOKEN_ID] = id }
    }

    suspend fun clearSession() {
        cachedToken = null
        cachedAuthId = null

        dataStore.edit { prefs ->
            prefs.remove(TOKEN_KEY)
            prefs.remove(TOKEN_ID)
        }

    }

}