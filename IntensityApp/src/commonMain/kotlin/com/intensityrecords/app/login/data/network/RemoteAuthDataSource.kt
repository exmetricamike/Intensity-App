package com.intensityrecords.app.login.data.network

import com.intensityrecords.app.login.data.dto.AuthRequestDto
import com.intensityrecords.app.login.data.dto.AuthResponseDto


interface RemoteAuthDataSource {
    suspend fun login(request: AuthRequestDto): Result<AuthResponseDto>
}