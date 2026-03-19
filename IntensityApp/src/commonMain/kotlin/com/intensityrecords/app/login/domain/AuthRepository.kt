package com.intensityrecords.app.login.domain


interface AuthRepository {
    suspend fun login(username: String, password: String): Result<AuthItem>

    suspend fun saveSession(token: String, id: String)
}