package com.intensityrecord.sensor.data.repository

import com.intensityrecord.core.data.ApiEndpointResolver
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
import com.intensityrecord.sensor.data.network.ZensiApi
import com.intensityrecord.sensor.domain.SensorRepository
import com.intensityrecord.sensor.domain.SensorSettings

class DefaultSensorRepository(
    private val api: ZensiApi
) : SensorRepository {

    override suspend fun login(
        username: String,
        password: String
    ): Result<AuthResponseDto, DataError.Remote> {
        val baseUrl = ApiEndpointResolver.resolveForLogin(username)
        return api.login(baseUrl, AuthRequestDto(username, password))
    }

    override suspend fun getStatus(
        deviceId: String
    ): Result<StatusResponseDto, DataError.Remote> {
        return api.getStatus(deviceId)
    }

    override suspend fun changeSensorSettings(
        settings: SensorSettings
    ): Result<SensorSettingsChangeResponseDto, DataError.Remote> {
        return api.changeSensorSettings(settings)
    }

    override suspend fun sendUserAction(
        sensorId: String?,
        action: String,
        parameter: String,
        deviceId: String
    ): Result<UserActionResponseDto, DataError.Remote> {
        return api.sendUserAction(
            UserActionDto(
                sensorId = sensorId,
                action = action,
                parameter = parameter,
                deviceId = deviceId
            )
        )
    }

    override suspend fun calibrateSensor(
        padId: String
    ): Result<UserActionResponseDto, DataError.Remote> {
        return api.calibrateSensor(padId)
    }

    override suspend fun getLogo(): Result<LogoResponseDto, DataError.Remote> {
        return api.getLogo()
    }

    override suspend fun getTimeslots(): Result<TimeSlotsResponseDto, DataError.Remote> {
        return api.getTimeslots()
    }

    override suspend fun getChart(
        sensorId: String
    ): Result<String, DataError.Remote> {
        return api.getChart(sensorId)
    }

    override suspend fun getUserPin(): Result<PinResponseDto, DataError.Remote> {
        return api.getUserPin()
    }
}
