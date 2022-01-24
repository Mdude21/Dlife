package com.example.developerslife.Networking

import com.example.developerslife.Networking.models.GifItem
import com.example.developerslife.Networking.models.GifItems

class GifRepository {

    suspend fun getTop(page: Int) : GifItems {
        return Networking.developersLifeApi.getTop(page)
    }

    suspend fun getHot(page: Int) : GifItems {
        return Networking.developersLifeApi.getHot(page)
    }

    suspend fun getLatest(page: Int) : GifItems {
        return Networking.developersLifeApi.getLatest(page)
    }

    suspend fun getRandom() : GifItem {
        return Networking.developersLifeApi.getRandom()
    }
}