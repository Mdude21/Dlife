package com.example.developerslife.ui.main

import android.util.Log
import androidx.lifecycle.*
import com.example.developerslife.Networking.GifRepository
import com.example.developerslife.Networking.models.GifItem
import com.example.developerslife.Networking.models.GifItems
import com.example.developerslife.R
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class PageViewModel(private val tabs: Tabs) : ViewModel() {

    private var page = 0

    private val repository = GifRepository()

    private var currentGifJob: Job? = null

    private var default = GifItem(0, R.drawable.abc_vector_test.toString(), "Sorry, undefined error. Please try again")

    private val gifLiveData  = MutableLiveData<GifItem>()

    val gif: LiveData<GifItem>
        get() = gifLiveData

    private val gifListLiveData = MutableLiveData<GifItems>()

    val gifList : LiveData<GifItems>
        get() = gifListLiveData

    private var list = ArrayList<GifItem>()
//    private var list = MyApp

//    private var listLiveData = MutableLiveData<ArrayList<GifItem>>()
//
//    val list: LiveData<ArrayList<GifItem>>
//        get() = listLiveData

    private var index : Int = 0

    private var gifStack = Stack<GifItem>()



    fun next() {
//        viewModelScope.launch {
            index++
            if (index == list.size){
                when (tabs){
                    Tabs.RANDOM -> {}
                    Tabs.LATEST -> page++
                    Tabs.TOP -> page++
                }
                getGif()
//                list.add(gifLiveData.value!!)
//                Log.d("nastya", "index = ${index}, desc = ${list[index].description}")
            }
            else{
                gifLiveData.postValue(list[index])
            }
//        }
    }

    fun prev() {
        if (index > 0)
            index--
        gifLiveData.postValue(list[index])
        Log.d("adel", "index = ${index}, desc = ${list[index].description}")
    }

    suspend fun addList(list : ArrayList<GifItem>, item: GifItem){
        list.add(item)
    }

    fun getGif(){
        currentGifJob?.cancel()
        currentGifJob = viewModelScope.launch {
            runCatching {
                when (tabs) {
                    Tabs.RANDOM -> {
                        repository.getRandom()
                    }
                    Tabs.LATEST -> {
                        repository.getLatest(page)
                    }
                    Tabs.TOP -> {
                        repository.getTop(page)
                    }
                }
            }.onSuccess {
                when (it) {
                    is GifItems -> {
                        gifListLiveData.postValue(it)
                        for (element in it.list)
                            list.add(element!!)
                        gifLiveData.postValue(list[index])
                    }
                    is GifItem -> {
                        list.add(it)
                        gifLiveData.postValue(it)
                    }
                }

//                Log.d("adel", "${it}")

            }.onFailure {
//                Log.d("azamat", "${it}")
                gifLiveData.postValue(default)

            }
//            if (result.isSuccess){
//                result.getOrNull()?.let {
//                    val mem = it.first()
//                    list.add(mem!!)
//                    Log.d("azamat", "${mem}")
//                    if (mem.gifURL.isNotBlank()) {
//                        gifLiveData.value = mem!!
//                    }
//                }
//            }
//            else
//                Log.d("azamat", "${result}")
//            }.onSuccess {
//                val mem = it.first()
//                    gifLiveData.postValue(mem!!)
//                    list.add(gifLiveData.value!!)
//                gifLiveData.value = it

//                gifLiveData.postValue(it)
//                gifListLiveData.postValue(it)
//                if (list.isEmpty()) {
////                    addList(list, it)
////                    list.add(it)
//                    Log.d("azamat", "index = ${index}, desc = ${list[index].description}")
//                }
//            }.onFailure {
//                gifLiveData.postValue(default)
//                Log.d("asdasd", "345 ${it}")
//
//            }
        }
    }

}