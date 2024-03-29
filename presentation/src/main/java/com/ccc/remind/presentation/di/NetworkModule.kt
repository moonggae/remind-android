package com.ccc.remind.presentation.di

import com.ccc.remind.data.source.remote.AuthRemoteService
import com.ccc.remind.data.source.remote.FriendRemoteService
import com.ccc.remind.data.source.remote.ImageRemoteService
import com.ccc.remind.data.source.remote.MindCardBookmarkRemoteService
import com.ccc.remind.data.source.remote.MindMemoRemoteService
import com.ccc.remind.data.source.remote.MindRemoteService
import com.ccc.remind.data.source.remote.UserRemoteService
import com.ccc.remind.data.util.LocalDataTypeConverter
import com.ccc.remind.presentation.di.network.AuthOkHttpClient
import com.ccc.remind.presentation.di.network.AuthRetrofit
import com.ccc.remind.presentation.di.network.InterceptorOkHttpClient
import com.ccc.remind.presentation.di.network.InterceptorRetrofit
import com.ccc.remind.presentation.di.network.TokenInterceptor
import com.ccc.remind.presentation.util.Constants.BASE_URL
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.lang.reflect.Type
import java.time.ZonedDateTime
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor(
        logger = { message ->
            if(message.contains("</svg>")) {
                Timber.tag("OkHttp").d("<svg file>")
                return@HttpLoggingInterceptor
            }
            else if (!message.startsWith("{") && !message.startsWith("[")) {
                Timber.tag("OkHttp").d(message)
                return@HttpLoggingInterceptor
            }
            try {
                Timber.tag("OkHttp").d(GsonBuilder().setPrettyPrinting().create().toJson(JsonParser.parseString(message)))
            } catch (m: JsonSyntaxException) {
                Timber.tag("OkHttp").d(message)
            }
        }
    ).apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @InterceptorOkHttpClient
    @Provides
    @Singleton
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        tokenInterceptor: TokenInterceptor
    ): OkHttpClient = OkHttpClient
        .Builder()
        .addNetworkInterceptor(httpLoggingInterceptor)
        .addInterceptor(tokenInterceptor)
        .build()

    @AuthOkHttpClient
    @Provides
    @Singleton
    fun provideAuthOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient = OkHttpClient
        .Builder()
        .addNetworkInterceptor(httpLoggingInterceptor)
        .build()

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create(
            GsonBuilder()
                .serializeNulls()
                .registerTypeAdapter(ZonedDateTime::class.java, LocalDataTypeConverter()) // ZonedDateTime converter
                .create() // include null value
        )

    private val nullOnEmptyConverterFactory = object : Converter.Factory() {
        fun converterFactory() = this
        override fun responseBodyConverter(type: Type, annotations: Array<out Annotation>, retrofit: Retrofit) = object :
            Converter<ResponseBody, Any?> {
            val nextResponseBodyConverter = retrofit.nextResponseBodyConverter<Any?>(converterFactory(), type, annotations)
            override fun convert(value: ResponseBody) = if (value.contentLength() != 0L) {
                try{
                    nextResponseBodyConverter.convert(value)
                }catch (e:Exception){
                    e.printStackTrace()
                    null
                }
            } else{
                null
            }
        }
    }

    @InterceptorRetrofit
    @Provides
    @Singleton
    fun provideRetrofit(
        @InterceptorOkHttpClient okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(nullOnEmptyConverterFactory)
        .addConverterFactory(gsonConverterFactory)
        .build()


    @AuthRetrofit
    @Provides
    @Singleton
    fun provideAuthRetrofit(
        @AuthOkHttpClient okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(nullOnEmptyConverterFactory)
        .addConverterFactory(gsonConverterFactory)
        .build()

    @Provides
    @Singleton
    fun provideUserService(
        @InterceptorRetrofit retrofit: Retrofit
    ): UserRemoteService = retrofit.create(UserRemoteService::class.java)


    @Provides
    @Singleton
    fun provideMindService(
        @InterceptorRetrofit retrofit: Retrofit
    ): MindRemoteService = retrofit.create(MindRemoteService::class.java)

    @Provides
    @Singleton
    fun provideImageService(
        @InterceptorRetrofit retrofit: Retrofit
    ): ImageRemoteService = retrofit.create(ImageRemoteService::class.java)

    @Provides
    @Singleton
    fun provideMindMemoService(
        @InterceptorRetrofit retrofit: Retrofit
    ): MindMemoRemoteService = retrofit.create(MindMemoRemoteService::class.java)

    @Provides
    @Singleton
    fun provideAuthService(
        @AuthRetrofit retrofit: Retrofit
    ): AuthRemoteService = retrofit.create(AuthRemoteService::class.java)

    @Provides
    @Singleton
    fun provideFriendService(
        @InterceptorRetrofit retrofit: Retrofit
    ): FriendRemoteService = retrofit.create(FriendRemoteService::class.java)

    @Provides
    @Singleton
    fun provideMindCardBookmarkService(
        @InterceptorRetrofit retrofit: Retrofit
    ): MindCardBookmarkRemoteService = retrofit.create(MindCardBookmarkRemoteService::class.java)
}