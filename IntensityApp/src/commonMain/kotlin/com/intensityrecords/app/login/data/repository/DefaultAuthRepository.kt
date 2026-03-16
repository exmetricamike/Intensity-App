package com.intensityrecords.app.login.data.repository

import com.intensityrecords.app.core.data.SessionProvider
import com.intensityrecords.app.login.data.dto.AuthRequestDto
import com.intensityrecords.app.login.data.mappers.toDomain
import com.intensityrecords.app.login.data.network.RemoteAuthDataSource
import com.intensityrecords.app.login.domain.AuthItem
import com.intensityrecords.app.login.domain.AuthRepository

class DefaultAuthRepository(
    private val remoteDataSource: RemoteAuthDataSource,
    private val sessionProvider: SessionProvider
) : AuthRepository {

    override suspend fun login(username: String, password: String): Result<AuthItem> {
        val request = AuthRequestDto(
            username = username,
            password = password,
            deviceId = "my-device-001",
            deviceModel = "iPhone 15",
            osVersion = "iOS 17",
            appVersion = "1.0.0"
        )

        return remoteDataSource.login(request).mapCatching { it.toDomain() }
    }

    override suspend fun saveSession(token: String, id: String) {
        sessionProvider.saveToken(token, id)
    }

}