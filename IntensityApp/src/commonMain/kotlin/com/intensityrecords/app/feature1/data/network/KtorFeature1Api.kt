package com.intensityrecord.sensor.data.network

import com.intensityrecord.core.data.ApiEndpointResolver
import com.intensityrecord.core.data.safeCall
import com.intensityrecord.core.domain.DataError
import com.intensityrecord.core.domain.Result
import com.intensityrecord.sensor.data.dto.AuthRequestDto
import com.intensityrecord.sensor.data.dto.AuthResponseDto
import com.intensityrecord.sensor.data.dto.LogoResponseDto
import com.intensityrecord.sensor.data.dto.PinResponseDto
import com.intensityrecord.sensor.data.dto.SensorSettingsChangeResponseDto
import com.intensityrecord.sensor.data.dto.StatusResponseDto
import com.intensityrecord.sensor.data.dto.TimeSlotsResponseDto
import com.intensityrecord.sensor.data.dto.UserActionDto
import com.intensityrecord.sensor.data.dto.UserActionResponseDto
import com.intensityrecord.sensor.domain.SensorSettings
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.ensureActive
import kotlin.coroutines.coroutineContext

class KtorZensiApi(
    private val httpClient: HttpClient
) : ZensiApi {

    override suspend fun login(
        baseUrl: String,
        request: AuthRequestDto
    ): Result<AuthResponseDto, DataError.Remote> = safeCall {
        httpClient.post("${baseUrl}api-token-auth/") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
    }

    override suspend fun getStatus(
        deviceId: String
    ): Result<StatusResponseDto, DataError.Remote> = safeCall {
        httpClient.get(ApiEndpointResolver.getV2Url("status")) {
            if (deviceId.isNotBlank()) {
                parameter("deviceid", deviceId)
            }
        }
    }

    override suspend fun changeSensorSettings(
        settings: SensorSettings
    ): Result<SensorSettingsChangeResponseDto, DataError.Remote> = safeCall {
        httpClient.post(ApiEndpointResolver.getVersionedUrl("sc")) {
            contentType(ContentType.Application.Json)
            setBody(settings)
        }
    }

    override suspend fun sendUserAction(
        action: UserActionDto
    ): Result<UserActionResponseDto, DataError.Remote> = safeCall {
        httpClient.post(ApiEndpointResolver.getVersionedUrl("ui")) {
            contentType(ContentType.Application.Json)
            setBody(action)
        }
    }

    override suspend fun calibrateSensor(
        padId: String
    ): Result<UserActionResponseDto, DataError.Remote> = safeCall {
        httpClient.post(ApiEndpointResolver.getV2Url("retare")) {
            contentType(ContentType.Application.Json)
            setBody(mapOf("pad_id" to padId))
        }
    }

    override suspend fun getLogo(): Result<LogoResponseDto, DataError.Remote> = safeCall {
        httpClient.get(ApiEndpointResolver.getV2Url("logo"))
    }

    override suspend fun getTimeslots(): Result<TimeSlotsResponseDto, DataError.Remote> = safeCall {
        httpClient.get(ApiEndpointResolver.getV2Url("timeslots"))
    }

    override suspend fun getChart(sensorId: String): Result<String, DataError.Remote> {
        return try {
            val response = httpClient.get("${ApiEndpointResolver.getBaseUrl()}api/v2/appchart/$sensorId")
            when (response.status.value) {
                in 200..299 -> Result.Success(response.bodyAsText())
                else -> Result.Error(DataError.Remote.UNKNOWN)
            }
        } catch (e: Exception) {
            coroutineContext.ensureActive()
            Result.Error(DataError.Remote.UNKNOWN)
        }
    }

    override suspend fun getUserPin(): Result<PinResponseDto, DataError.Remote> = safeCall {
        httpClient.get(ApiEndpointResolver.getVersionedUrl("pincode"))
    }
}
