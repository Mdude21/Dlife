package com.example.developerslife.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.developerslife.databinding.ActivityMainBinding
import com.example.developerslife.presentation.fragments.PlaceholderFragment
import com.example.developerslife.presentation.adapter.SectionsPagerAdapter
import com.example.developerslife.presentation.Tabs
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var pageAdapter: SectionsPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pageAdapter = SectionsPagerAdapter(this)
        enumValues<Tabs>().forEach {
            pageAdapter.addFragment(
                it.tab,
                PlaceholderFragment.newInstance(it)
            )
        }

        binding.viewPager.adapter = pageAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = pageAdapter.getTabs()[position]
        }.attach()

    }
}