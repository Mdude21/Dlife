package com.example.developerslife.ui.main

import androidx.lifecycle.*
import com.example.developerslife.Networking.GifRepository
import com.example.developerslife.Networking.models.GifItem
import com.example.developerslife.Networking.models.GifItems
import com.example.developerslife.R
import kotlinx.coroutines.launch
import kotlin.collections.ArrayList

class PageViewModel(private val tabs: Tabs) : ViewModel() {

    private var page = 0

    private var error : Boolean = true

    private val repository = GifRepository()


    private var default = GifItem(0, R.drawable.abc_vector_test.toString(), "Sorry, undefined error. Please try again")

    private val gifLiveData  = MutableLiveData<GifItem>()

    val gif: LiveData<GifItem>
        get() = gifLiveData

    private var list = ArrayList<GifItem>()

    private val isFirstLiveData = MutableLiveData<Boolean>(false)

    val isFirst: LiveData<Boolean>
        get() = isFirstLiveData

    private var index = 0

    fun isFirstIndex(){
        if (index == 0)
            isFirstLiveData.postValue(false)
        else
            isFirstLiveData.postValue(true)
    }

    fun next() {
            if (error)
                index++
            if (index == list.size){
                when (tabs){
                    Tabs.RANDOM -> {}
                    Tabs.LATEST -> page++
                    Tabs.TOP -> page++
                }
                getGif()
            }
            else{
                gifLiveData.postValue(list[index])
            }
    }

    fun prev() {
        error = true
        if (index > 0)
            index--
        if (list.isNotEmpty())
            gifLiveData.postValue(list[index])
    }

    fun getGif(){
        error = true
        if (index != list.size)
            gifLiveData.postValue(list[index])
        else {
            viewModelScope.launch {
                runCatching {
                    when (tabs) {
                        Tabs.RANDOM -> repository.getRandom()
                        Tabs.LATEST -> repository.getLatest(page)
                        Tabs.TOP -> repository.getTop(page)
                        }
                }.onSuccess {
                    when (it) {
                        is GifItems -> {
                            for (element in it.list)
                                list.add(element!!)
                            gifLiveData.postValue(list[index])
                        }
                        is GifItem -> {
                            list.add(it)
                            gifLiveData.postValue(it)
                        }
                    }
                }.onFailure {
                    error = false
                    gifLiveData.postValue(default)
                }
            }
        }
    }
}