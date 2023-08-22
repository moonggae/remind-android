package com.ccc.remind.presentation.di.network

import com.ccc.remind.domain.entity.user.JwtToken
import com.ccc.remind.domain.repository.AuthRepository
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenInterceptor @Inject constructor(private val authRepository: AuthRepository) : Interceptor {
    private lateinit var accessToken: String
    private lateinit var refreshToken: String
    private val isTokenBeingRefreshed = MutableStateFlow(false)

    private fun updateToken(token: JwtToken) {
        accessToken = token.accessToken
        refreshToken = token.refreshToken
    }

    private fun waitForTokenRefresh() {
        if (isTokenBeingRefreshed.value) {
            try {
                runBlocking {
                    isTokenBeingRefreshed.collect {
                        if (!it) {
                            cancel()
                        }
                    }
                }
            } catch (_: Throwable) { }
        }
    }

    private fun initToken() {
        if (!this::accessToken.isInitialized) {
            val token = runBlocking { authRepository.getToken() }
            if (token != null) {
                updateToken(token)
            }
        }
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        waitForTokenRefresh()
        initToken()

        val request = chain.request().putTokenHeader(accessToken)
        var response: Response = chain.proceed(request)

        if (response.isTokenExpired()) {
            isTokenBeingRefreshed.value = true
            runBlocking {
                val newToken = authRepository.getNewToken(refreshToken)
                if (newToken != null) {
                    updateToken(newToken)
                    val newRequest = chain.request().putTokenHeader(accessToken)
                    response.close()
                    response = chain.proceed(newRequest)
                    isTokenBeingRefreshed.value = false
                }
            }
        }
        return response
    }
}