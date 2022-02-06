package com.example.developerslife.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.developerslife.presentation.Tabs

class PageViewModelFactory(private val tabs: Tabs) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PageViewModel(tabs) as T
    }
}