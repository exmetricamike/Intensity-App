package com.intensityrecords.app.login.data.network

import com.intensityrecords.app.login.data.dto.AuthRequestDto
import com.intensityrecords.app.login.data.dto.AuthResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class KtorRemoteAuthDataSource(
    private val httpClient: HttpClient
) : RemoteAuthDataSource {

    override suspend fun login(request: AuthRequestDto): Result<AuthResponseDto> {
        return try {
            val response = httpClient.post("https://intensityapi.exmetrica.be/api-token-auth/") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }
            Result.success(response.body<AuthResponseDto>())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}