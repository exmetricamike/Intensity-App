package com.intensityrecord.sensor.data.network

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

interface ZensiApi {
    suspend fun login(baseUrl: String, request: AuthRequestDto): Result<AuthResponseDto, DataError.Remote>
    suspend fun getStatus(deviceId: String): Result<StatusResponseDto, DataError.Remote>
    suspend fun changeSensorSettings(settings: SensorSettings): Result<SensorSettingsChangeResponseDto, DataError.Remote>
    suspend fun sendUserAction(action: UserActionDto): Result<UserActionResponseDto, DataError.Remote>
    suspend fun calibrateSensor(padId: String): Result<UserActionResponseDto, DataError.Remote>
    suspend fun getLogo(): Result<LogoResponseDto, DataError.Remote>
    suspend fun getTimeslots(): Result<TimeSlotsResponseDto, DataError.Remote>
    suspend fun getChart(sensorId: String): Result<String, DataError.Remote>
    suspend fun getUserPin(): Result<PinResponseDto, DataError.Remote>
}
