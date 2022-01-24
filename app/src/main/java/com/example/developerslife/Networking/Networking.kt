package com.example.developerslife.Networking

import android.util.Log
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object Networking {

    private val okHttpClient = OkHttpClient()
        .newBuilder()
        .addInterceptor(HttpLoggingInterceptor(){
            Log.d("qweqwe", "${it}")
        }
            .setLevel(HttpLoggingInterceptor.Level.BODY)
        )
        .addInterceptor {
            val request = it.request()
            val url = request.url.newBuilder().addQueryParameter("json", "true").build()
            Log.d("qweqwe", "${it}")
            it.proceed(request.newBuilder().url(url).build())
        }
        .build()


    private val contentType = "application/json".toMediaType()

    private val json = Json { ignoreUnknownKeys = true }

    private val retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl("https://developerslife.ru/")
//        .addConverterFactory(MoshiConverterFactory.create())
        .addConverterFactory(json.asConverterFactory(contentType))
        .build()

   val developersLifeApi : DevelopersLifeApi
        get() = retrofit.create(DevelopersLifeApi::class.java)
}