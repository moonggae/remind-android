package com.ccc.remind.presentation.di.network

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
    private val isGettingToken = MutableStateFlow(false)

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

    override fun intercept(chain: Interceptor.Chain): Response {
        if(isGettingToken.value) {
            waitForTokenRefresh()
        }

        val accessToken = runBlocking {
            authRepository.getToken()?.accessToken ?: ""
        }

        val request = chain.request().putTokenHeader(accessToken)
        var response: Response = chain.proceed(request)

        if (response.isTokenExpired()) {
            isGettingToken.value = true
            runBlocking {
                val newToken = authRepository.getNewToken()
                if (newToken != null) {
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