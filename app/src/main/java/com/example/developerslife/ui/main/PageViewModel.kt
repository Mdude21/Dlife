package com.example.developerslife.ui.main

import android.util.Log
import androidx.lifecycle.*
import com.example.developerslife.Networking.GifRepository
import com.example.developerslife.Networking.models.GifItem
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

    private var list = ArrayList<GifItem>()

//    private var listLiveData = MutableLiveData<ArrayList<GifItem>>()
//
//    val list: LiveData<ArrayList<GifItem>>
//        get() = listLiveData

    private var index : Int = 0


    fun next() {
        viewModelScope.launch {
            index++
            if (index == list.size){
                getGif()
                list.add(index, gifLiveData.value!!)
            }
            else{
                gifLiveData.postValue(list[index])
            }
        }
    }

    fun prev() {
        if (index > 0)
            index--
        gifLiveData.postValue(list[index])
    }

    suspend fun addList(list : ArrayList<GifItem>, item: GifItem){
        list.add(item)
    }

    fun getGif(){
        currentGifJob?.cancel()

        currentGifJob = viewModelScope.launch {
            runCatching {
//                when (tabs) {
//                    Tabs.HOT -> {
//                        repository.getHot(page)
//                    }
//                    Tabs.RANDOM -> {
                        repository.getRandom()
//                    }
//                    Tabs.TOP -> {
//                        repository.getTop(page)
//                    }
//                }
            }.onSuccess {
                gifLiveData.postValue(it)
                if (list.size == 0)
//                    addList(list, it)
                    list.add(it)
                Log.d("asdasd", "345 ${it}")
            }.onFailure {
                gifLiveData.postValue(default)
                Log.d("asdasd", "345 ${it}")

            }
        }
    }

}