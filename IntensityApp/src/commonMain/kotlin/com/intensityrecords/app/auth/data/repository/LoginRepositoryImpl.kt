package com.intensityrecord.auth.data.repository

import com.intensityrecord.auth.TokenStorage
import com.intensityrecord.auth.data.dto.LoginRequest
import com.intensityrecord.auth.data.dto.LoginResponse
import com.intensityrecord.core.data.safeCall
import com.intensityrecord.core.domain.DataError
import com.intensityrecord.core.domain.Result
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

private const val URL_LOGIN = "/auth/login"
private const val URL_USER = "/auth/me"


class LoginRepositoryImpl(
    private val httpClient: HttpClient,
    private val tokenStorage: TokenStorage
) : LoginRepository {

    override suspend fun login(
        username: String,
        password: String
    ): Result<LoginResponse, DataError.Remote> = safeCall<LoginResponse> {
        val response = httpClient.post(URL_LOGIN) {
            contentType(ContentType.Application.Json)
            setBody(LoginRequest(username, password))
        }

        // Parse the body explicitly
        val loginResponse: LoginResponse = response.body()

        // Store tokens if present
        loginResponse.accessToken?.let { access ->
            loginResponse.refreshToken?.let { refresh ->
                tokenStorage.saveTokens(access, refresh)
            }
        }

        response
    }

    override suspend fun user(): Result<LoginResponse, DataError.Remote> = safeCall<LoginResponse> {
        httpClient.get(URL_USER)
    }

    override suspend fun logout() {
        tokenStorage.clearTokens()
    }
}