package com.example.developerslife.presentation.viewmodels

import androidx.lifecycle.*
import com.example.developerslife.data.GifRepository
import com.example.developerslife.data.models.GifItem
import com.example.developerslife.data.models.GifItems
import com.example.developerslife.R
import com.example.developerslife.presentation.Tabs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PageViewModel(private val tabs: Tabs) : ViewModel() {

    private var page = 0

    private var error: Boolean = true

    private val repository = GifRepository()


    private var default = GifItem(0, R.drawable.abc_vector_test.toString(), DEFAULT_ERROR)

    private val gifLiveData = MutableLiveData<GifItem>()

    val gif: LiveData<GifItem>
        get() = gifLiveData

    private var list = mutableListOf<GifItem>()

    private val isFirstLiveData = MutableLiveData<Boolean>(false)

    val isFirst: LiveData<Boolean>
        get() = isFirstLiveData

    private var index = 0

    private var isFinish = true

    fun isFirstIndex() {
        isFirstLiveData.postValue(index != 0)
    }

    fun next() {
        if (isFinish) {
            if (error)
                index++
            if (index == list.size) {
                when (tabs) {
                    Tabs.RANDOM -> {}
                    Tabs.LATEST -> page++
                    Tabs.TOP -> page++
                }
                getGif()
            } else
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

    fun getGif() {
        error = true
        isFinish = false
        if (index != list.size) {
            gifLiveData.postValue(list[index])
            isFinish = true
        } else {
            viewModelScope.launch(Dispatchers.IO) {
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
                                list.add(element)
                            gifLiveData.postValue(list[index])
                        }
                        is GifItem -> {
                            list.add(it)
                            gifLiveData.postValue(it)
                        }
                    }
                    isFinish = true
                }.onFailure {
                    error = false
                    gifLiveData.postValue(default)
                    isFinish = true
                }
            }
        }
    }

    companion object {
        private const val DEFAULT_ERROR = "Sorry, undefined error. Please try again"
    }
}