package com.ccc.remind.presentation.di

import android.util.Log
import com.ccc.remind.data.source.remote.LoginRemoteService
import com.ccc.remind.presentation.MyApplication
import com.ccc.remind.presentation.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = "http://10.0.2.2:3000"

    private const val TAG = "NetworkModule"

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideAuthorizationInterceptor() : Interceptor {
        return Interceptor { chain ->
            val newRequestBuilder = chain.request().newBuilder()
            val accessToken = Constants.accessToken
            Log.d(TAG, "NetworkModule - provideAuthorizationInterceptor - accessToken: ${accessToken}")
            if(accessToken != null) {
                newRequestBuilder.addHeader("authorization", "Bearer $accessToken")
            }
            chain.proceed(newRequestBuilder.build())
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor, authorizationInterceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addNetworkInterceptor(httpLoggingInterceptor)
            .addInterceptor(authorizationInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

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
    ): LoginRemoteService {
        return retrofit.create(LoginRemoteService::class.java)
    }
}