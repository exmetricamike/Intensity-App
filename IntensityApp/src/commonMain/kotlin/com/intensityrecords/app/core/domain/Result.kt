package com.intensityrecord.core.domain

sealed interface Result<out D, out E: Error> {
    /*
    * This defines a sealed interface named Result with two type parameters:
    * D: The data type on success (e.g., a decoded response, list, etc.).
    * E: The error type on failure, constrained to be a subtype of Error.
    *
    *
    * <out D, out E: Error> out D and out E are covariant, meaning they can be safely used as return types.
    * This enables substituting Result<SubType, SubError> where Result<SuperType, SuperError> is expected.
    * E: Error: This is a type constraint. E must be a subtype of the Error interface or class.
*
    * */

    //The Result interface has two implementations:
    // Success: A generic data class that wraps a successful value of type D.
    //Implements Result<D, Nothing>, meaning:
    //
    //D is the successful result.
    //
    //Nothing is used as the error type, indicating no error is present.
    // Usage: val result: Result<String, MyError> = Result.Success("Hello")
    data class Success<out D>(val data: D): Result<D, Nothing>
    /*
    * Another generic data class that wraps an error of type E.
    * Implements Result<Nothing, E>, meaning:
    * No data is available (Nothing for the success value).
    * An error of type E is present.
    * The type constraint ensures that the error must conform to your custom Error interface/class from your project.
    * */
    // Usage val result: Result<String, MyError> = Result.Error(MyError.Timeout)
    data class Error<out E: com.intensityrecord.core.domain.Error>(val error: E):
        Result<Nothing, E>
}

inline fun <T, E: Error, R> Result<T, E>.map(map: (T) -> R): Result<R, E> {
    return when(this) {
        is Result.Error -> Result.Error(error)
        is Result.Success -> Result.Success(map(data))
    }
}

fun <T, E: Error> Result<T, E>.asEmptyDataResult(): EmptyResult<E> {
    return map {  }
}

inline fun <T, E: Error> Result<T, E>.onSuccess(action: (T) -> Unit): Result<T, E> {
    return when(this) {
        is Result.Error -> this
        is Result.Success -> {
            action(data)
            this
        }
    }
}
inline fun <T, E: Error> Result<T, E>.onError(action: (E) -> Unit): Result<T, E> {
    return when(this) {
        is Result.Error -> {
            action(error)
            this
        }
        is Result.Success -> this
    }
}

typealias EmptyResult<E> = Result<Unit, E>