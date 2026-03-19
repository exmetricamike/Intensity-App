package com.intensityrecords.app.login.data.mappers

import com.intensityrecords.app.login.data.dto.AuthResponseDto
import com.intensityrecords.app.login.domain.AuthItem


fun AuthResponseDto.toDomain(): AuthItem {
    return AuthItem(
        token = this.token,
        hotelId = this.hotelId
    )
}