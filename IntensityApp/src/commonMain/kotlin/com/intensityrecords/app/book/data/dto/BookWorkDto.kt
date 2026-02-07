package com.intensityrecord.book.data.dto

import kotlinx.serialization.Serializable

@Serializable(with = BookWorkDtoSerializer::class) // this specifies the custome serializer
data class BookWorkDto(
    val description: String? = null
)