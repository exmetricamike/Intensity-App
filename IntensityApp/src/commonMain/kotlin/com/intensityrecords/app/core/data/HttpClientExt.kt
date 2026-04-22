package com.intensityrecords.app.core.data

import com.intensityrecords.app.core.domain.DataError.Remote
import com.intensityrecords.app.core.domain.Result
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive

suspend inline fun <reified T> safeCall(
    execute: () -> HttpResponse
): Result<T, Remote> {
    val response = try {
        execute()
    } catch(e: SocketTimeoutException) {
        return Result.Error(Remote.REQUEST_TIMEOUT)
    } catch(e: UnresolvedAddressException) {
        return Result.Error(Remote.NO_INTERNET)
    } catch (e: Exception) {
        currentCoroutineContext().ensureActive()
        return Result.Error(Remote.UNKNOWN)
    }

    return responseToResult(response)
}

suspend inline fun <reified T> responseToResult(
    response: HttpResponse
): Result<T, Remote> {
    return when(response.status.value) {
        in 200..299 -> {
            try {
                Result.Success(response.body<T>())
            } catch(e: NoTransformationFoundException) {
                Result.Error(Remote.SERIALIZATION)
            }
        }
        404 -> Result.Error(Remote.NOT_FOUND)
        408 -> Result.Error(Remote.REQUEST_TIMEOUT)
        429 -> Result.Error(Remote.TOO_MANY_REQUESTS)
        in 500..599 -> Result.Error(Remote.SERVER)
        else -> Result.Error(Remote.UNKNOWN)
    }
}