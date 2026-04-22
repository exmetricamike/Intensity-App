package com.intensityrecords.app.core.domain

sealed interface DataError : Error {
    enum class Remote : DataError {
        REQUEST_TIMEOUT,
        TOO_MANY_REQUESTS,
        NO_INTERNET,
        NOT_FOUND,
        SERVER,
        SERIALIZATION,
        UNKNOWN
    }
}