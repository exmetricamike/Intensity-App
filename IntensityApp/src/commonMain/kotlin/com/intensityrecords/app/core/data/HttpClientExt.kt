package com.intensityrecord.core.data

import com.intensityrecord.core.domain.DataError
import com.intensityrecord.core.domain.Result
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.ensureActive
import kotlin.coroutines.coroutineContext


/*function that safely executes http calls and catches error*/

/**
 * suspend – This function can be called from a coroutine or another suspend function, allowing it to run asynchronously without blocking a thread.
 * inline – The compiler will inline the function body at the call site, which can improve performance and allows non-local returns from the lambda.
 * <reified T> – The generic type T is reified, meaning you can use T::class or perform type checks (is T) at runtime — useful for deserialization or type-safe conversions.
 * execute – A lambda parameter that returns an HttpResponse. It represents the network call that should be executed safely.
 * Return type: Result<T, DataError.Remote> – A wrapper that indicates success (Result.Success) with data of type T, or failure (Result.Error) with a DataError.Remote that explains the cause.
 */
suspend inline fun <reified T> safeCall(
    execute: () -> HttpResponse
): Result<T, DataError.Remote> {
    val response = try {
        execute()
    } catch (e: SocketTimeoutException) {
        return Result.Error(DataError.Remote.REQUEST_TIMEOUT)
    } catch (e: UnresolvedAddressException) {
        return Result.Error(DataError.Remote.NO_INTERNET)
    } catch (e: Exception) {
        coroutineContext.ensureActive() //important
        return Result.Error(DataError.Remote.UNKNOWN)
    }

    return responseToResult(response)
}


/*
* Takes an HttpResponse from an api, tries to extract the data (json to type T) and returns the
* type of the response or error
*
*
*  suspend: This is a suspending function, meaning it can be paused and resumed later
*  typically used in coroutines to handle asynchronous code.

inline: This means the compiler will inline the function’s bytecode at the call site.
*  This is often done to reduce overhead and allow inlined use of features like reified generics.

<reified T>: This is a reified generic type parameter.
* Normally in Kotlin, generic type information is erased at runtime. Using reified allows you to retain the type information at runtime.
* You can then do things like response.body<T>() and know the type T at runtime.

response: HttpResponse: The input is an instance of HttpResponse, from Ktor .

Result<T, DataError.Remote>: The return type is a custom Result type, holding either:
A success value of type T, or
An error of type DataError.Remote.
* */
/**
 * Takes an HttpResponse from an api, tries to extract the data (json parsed into type T) and returns the
 * reified is needed because you need the type to be known at compile time inside the function
 * */
suspend inline fun <reified T> responseToResult(
    response: HttpResponse //full response with error code and body
): Result<T, DataError.Remote> {
    return when (response.status.value) {
        in 200..299 -> {
            try {
                //try to parse the response body as type T using response.body<T>().
                Result.Success(response.body<T>())
            } catch (e: NoTransformationFoundException) {
                //If there's a problem deserializing (e.g., no proper transformation exists to convert the HTTP body into type T),
                // it catches NoTransformationFoundException and returns a serialization error.
                Result.Error(DataError.Remote.SERIALIZATION)
            }
        }

        408 -> Result.Error(DataError.Remote.REQUEST_TIMEOUT)
        429 -> Result.Error(DataError.Remote.TOO_MANY_REQUESTS)
        in 500..599 -> {
            return Result.Error(DataError.Remote.SERVER)
        }

        else -> Result.Error(DataError.Remote.UNKNOWN)
    }
}
