package com.example.developerslife.Networking

import com.example.developerslife.Networking.models.GifItem
import com.example.developerslife.Networking.models.GifItems
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class GifRepository {

//    suspend fun getTop(page: Int) : Result<List<GifItem?>> = withContext(Dispatchers.IO) {
//        try {
//            val memes = Networking.developersLifeApi.getTop(page)
//            Result.success(memes.list)
//        } catch (exception : Exception) {
//            Result.failure(exception)
//        }
//    }
//
//    suspend fun getHot(page: Int) : Result<List<GifItem?>> = withContext(Dispatchers.IO) {
//        try {
//            val memes = Networking.developersLifeApi.getHot(page)
//            Result.success(memes.list)
//        } catch (exception : Exception) {
//            Result.failure(exception)
//        }
//    }
//
//    suspend fun getLatest(page: Int) : Result<List<GifItem?>> = withContext(Dispatchers.IO) {
//        try {
//            val memes = Networking.developersLifeApi.getLatest(page)
//            Result.success(memes.list)
//        } catch (exception : Exception) {
//            Result.failure(exception)
//        }
//    }

    suspend fun getHot(page: Int) : GifItems {
        return Networking.developersLifeApi.getHot(page)
    }

    suspend fun getLatest(page: Int) : GifItems {
        return Networking.developersLifeApi.getLatest(page)
    }

    suspend fun getTop(page: Int) : GifItems {
        return Networking.developersLifeApi.getTop(page)
    }

    suspend fun getRandom() : GifItem {
        return Networking.developersLifeApi.getRandom()
    }
}