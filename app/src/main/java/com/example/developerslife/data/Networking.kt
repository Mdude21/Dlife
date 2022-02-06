package com.example.developerslife.data

import com.example.developerslife.BuildConfig
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

object Networking {

    private val okHttpClient = OkHttpClient()
        .newBuilder()
        .addInterceptor(
            HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)
        )
        .addInterceptor {
            val request = it.request()
            val url = request.url.newBuilder().addQueryParameter("json", "true").build()
            it.proceed(request.newBuilder().url(url).build())
        }
        .build()

    private val contentType = "application/json".toMediaType()

    private val json = Json { ignoreUnknownKeys = true }

    private val retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(json.asConverterFactory(contentType))
        .build()

    val developersLifeApi: DevelopersLifeApi
        get() = retrofit.create(DevelopersLifeApi::class.java)
}