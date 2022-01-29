package com.example.developerslife.Networking

import com.example.developerslife.Networking.models.GifItem
import com.example.developerslife.Networking.models.GifItems
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class GifRepository {

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