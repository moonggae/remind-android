package com.ccc.remind.presentation.di.network

import android.util.Log
import com.ccc.remind.domain.entity.user.JwtToken
import com.ccc.remind.domain.repository.AuthRepository
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenInterceptor @Inject constructor(private val authRepository: AuthRepository) : Interceptor {
    private val TAG = "TokenInterceptor"
    private lateinit var accessToken: String
    private lateinit var refreshToken: String
    private val isGettingToken = MutableStateFlow(false)

    private fun updateToken(token: JwtToken) {
        accessToken = token.accessToken
        refreshToken = token.refreshToken
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

    private fun updateLoginToken(response: Response) {
        val paths = response.request.url.pathSegments
        if(paths[0] == "auth" && paths[0] == "login" && response.isSuccessful) {
            val json = JSONObject(response.body?.string() ?: "")
            val accessToken = json.getString("access_token")
            val refreshToken = json.getString("refresh_token")
            if(accessToken.isNotEmpty() && refreshToken.isNotEmpty()) {
                updateToken(JwtToken(accessToken, refreshToken))
            }
        }
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        if(isGettingToken.value) {
            waitForTokenRefresh()
        }
        if (!this::accessToken.isInitialized) {
            initToken()
        }


        Log.d(TAG, "TokenInterceptor - intercept - request: ${chain.request().url}")

        val request = chain.request().putTokenHeader(accessToken)
        var response: Response = chain.proceed(request)

        if (response.isTokenExpired()) {
            isGettingToken.value = true
            runBlocking {
                val newToken = authRepository.getNewToken(refreshToken)
                if (newToken != null) {
                    updateToken(newToken)
                    val newRequest = chain.request().putTokenHeader(accessToken)
                    response.close()
                    response = chain.proceed(newRequest)
                    isGettingToken.value = false
                }
            }
        }
        return response
    }
}