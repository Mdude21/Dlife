package com.example.developerslife.ui.main

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity){
    private val fragments = mutableListOf<Fragment>()
    private val tabs = mutableListOf<String>()

    override fun getItemCount(): Int {
        return fragments.size
    }


    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    fun addFragment(tab: String, fragment: Fragment) {
        fragments.add(fragment)
        tabs.add(tab)
    }

    fun getTabs(): List<String> {
        return tabs
    }
}