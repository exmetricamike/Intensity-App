package com.intensityrecord.auth

interface TokenStorage {
    suspend fun saveTokens(accessToken: String?, refreshToken: String?)
    suspend fun clearTokens()
    suspend fun getAccessToken(): String?
    suspend fun getRefreshToken(): String?

}