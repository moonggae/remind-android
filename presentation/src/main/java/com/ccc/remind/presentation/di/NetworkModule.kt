package com.ccc.remind.presentation.di

import com.ccc.remind.data.source.remote.LoginRemoteService
import com.ccc.remind.data.source.remote.MindRemoteService
import com.ccc.remind.data.source.remote.model.user.LoginResponse
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.charset.Charset
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = "http://10.0.2.2:3000"
    private var accessToken: String? = null

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    @Singleton
    fun provideAuthorizationInterceptor() : Interceptor = Interceptor { chain ->
        val newRequestBuilder = chain.request().newBuilder()
        if(accessToken != null) {
            newRequestBuilder.addHeader("authorization", "Bearer $accessToken")
        }

        val response = chain.proceed(newRequestBuilder.build())
        saveAccessToken(response)
        response
    }

    private fun saveAccessToken(response: Response) {
        if(response.isSuccessful && response.request.url.toUrl().path.lowercase().contains("auth/login")) {
            val responseSource = response.body?.source()
            responseSource?.request(Long.MAX_VALUE)
            val responseBody : String? = responseSource?.buffer?.clone()?.readString(Charset.forName("UTF-8"))
            if(responseBody != null) {
                try {
                    val token = Gson().fromJson(responseBody, LoginResponse::class.java)
                    accessToken = token.accessToken
                } catch (e: JsonSyntaxException) {
                    return
                }
            }
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        authorizationInterceptor: Interceptor
    ): OkHttpClient = OkHttpClient
        .Builder()
        .addNetworkInterceptor(httpLoggingInterceptor)
        .addInterceptor(authorizationInterceptor)
        .build()

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(gsonConverterFactory)
        .build()

    @Provides
    @Singleton
    fun provideUserService(
        retrofit: Retrofit
    ): LoginRemoteService = retrofit.create(LoginRemoteService::class.java)


    @Provides
    @Singleton
    fun provideMindService(
        retrofit: Retrofit
    ): MindRemoteService = retrofit.create(MindRemoteService::class.java)
}