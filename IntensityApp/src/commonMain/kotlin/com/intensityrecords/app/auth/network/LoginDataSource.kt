package com.intensityrecord.auth.network

import com.intensityrecord.auth.TokenStorage
import com.intensityrecord.auth.data.dto.LoginResponse
import com.intensityrecord.auth.data.repository.LoginRepository
import com.intensityrecord.core.domain.DataError
import com.intensityrecord.core.domain.Result

class LoginDataSource (
    private val repository: LoginRepository,
    private val tokenManager: TokenStorage,
) {
    suspend  fun login(username: String, password: String): Result<LoginResponse, DataError.Remote> {
        val result = repository.login(username, password)
        if (result is Result.Success ) {
            tokenManager.saveTokens(
                result.data.accessToken,
                result.data.refreshToken,
            )
        }
        return result
    }
}

//class LoginDataSource (
//    private val repository: LoginRepository,
//    private val tokenManager: TokenManager,
//) {
//    suspend operator fun invoke(username: String, password: String):{
//        val result = repository.login(username, password)
//        if (result is NetworkResult.Success) {
//            tokenManager.saveTokens(
//                result.data.accessToken,
//                result.data.refreshToken,
//            )
//        }
//        return result
//    }
//}
