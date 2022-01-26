package com.example.developerslife.Networking

import com.example.developerslife.Networking.models.GifItem
import com.example.developerslife.Networking.models.GifItems
import retrofit2.http.GET
import retrofit2.http.Path

interface DevelopersLifeApi {

    @GET("latest/{page}")
    suspend fun getLatest(@Path("page") page: Int) : GifItems

    @GET("top/{page}")
    suspend fun getTop(@Path("page") page: Int) : GifItems

    @GET("hot/{page}")
    suspend fun getHot(@Path("page") page: Int) : GifItems

    @GET("random")
    suspend fun getRandom() : GifItem
}