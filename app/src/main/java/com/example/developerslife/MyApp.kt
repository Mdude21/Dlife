package com.example.developerslife

import android.app.Application
import com.example.developerslife.Networking.models.GifItem

class MyApp : Application(){
    init {
        var list = ArrayList<GifItem>()
        var index: Int = 0
    }
}