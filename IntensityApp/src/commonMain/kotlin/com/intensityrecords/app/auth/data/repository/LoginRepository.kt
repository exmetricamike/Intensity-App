package com.intensityrecord.auth.data.repository

import com.intensityrecord.auth.data.dto.LoginResponse
import com.intensityrecord.core.domain.DataError
import com.intensityrecord.core.domain.Result

interface LoginRepository {
    suspend fun login(username: String, password: String): Result<LoginResponse, DataError.Remote>

    suspend fun logout()

    suspend fun user(): Result<LoginResponse, DataError.Remote>
}


