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
    private val TAG = "TokenInterceptor"
    private lateinit var accessToken: String
    private val isGettingToken = MutableStateFlow(false)

    fun removeToken() {
        accessToken = ""
    }

    private fun updateToken(token: JwtToken) {
        accessToken = token.accessToken
    }

    private fun waitForTokenRefresh() {
        try {
            runBlocking {
                isGettingToken.collect {
                    if (!it) {
                        cancel()
                    }
                }
            }
        } catch (_: Throwable) { }
    }

    private fun initToken() {
        val token = runBlocking { authRepository.getToken() }
        if (token != null) {
            updateToken(token)
        }
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        if(isGettingToken.value) {
            waitForTokenRefresh()
        }
        if (!this::accessToken.isInitialized || accessToken.isEmpty()) {
            initToken()
        }

        val request = chain.request().putTokenHeader(accessToken)
        var response: Response = chain.proceed(request)

        if (response.isTokenExpired()) {
            isGettingToken.value = true
            runBlocking {
                val newToken = authRepository.getNewToken()
                if (newToken != null) {
                    updateToken(newToken)
                    val newRequest = chain.request().putTokenHeader(accessToken)
                    response.close()
                    response = chain.proceed(newRequest)
                }
                isGettingToken.value = false
            }
        }
        return response
    }
}