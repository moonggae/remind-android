package com.ccc.remind.presentation.di.network

import com.ccc.remind.domain.entity.user.JwtToken
import com.ccc.remind.domain.repository.AuthRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenInterceptor @Inject constructor(private val authRepository: AuthRepository) : Interceptor {
    private lateinit var accessToken: String
    private lateinit var refreshToken: String

    private fun updateToken(token: JwtToken) {
        accessToken = token.accessToken
        refreshToken = token.refreshToken
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!this::accessToken.isInitialized) {
            runBlocking {
                val token = authRepository.getToken()
                if (token != null) {
                    updateToken(token)
                }
            }
        }

        val request = chain.request().putTokenHeader(accessToken)
        var response: Response = chain.proceed(request)

        if (response.code == 401) {
            runBlocking {
                val newToken = authRepository.getNewToken(refreshToken)
                if (newToken != null) {
                    updateToken(newToken)

                    val newRequest = chain.request().putTokenHeader(accessToken)
                    response.close()
                    response = chain.proceed(newRequest)
                }
            }
        }
        return response
    }
}