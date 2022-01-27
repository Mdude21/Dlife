package com.example.developerslife

import android.location.GnssAntennaInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.developerslife.ui.main.SectionsPagerAdapter
import com.example.developerslife.databinding.ActivityMainBinding
import com.example.developerslife.ui.main.PlaceholderFragment
import com.example.developerslife.ui.main.Tabs
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var pageAdapter: SectionsPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        setContentView(CustomView(this))

        pageAdapter = SectionsPagerAdapter(this)
        enumValues<Tabs>().forEach {
            pageAdapter.addFragment(
                it.tab,
                PlaceholderFragment.newInstance(it)
            )
        }
        binding.viewPager.adapter = pageAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) {tab, position ->
            tab.text = pageAdapter.getTabs()[position]
        }.attach()


//        binding.buttonActivityNext.setOnClickListener {
//
//        }
//        binding.buttonActivityPrev.setOnClickListener {
//            binding.viewPager.currentItem
//        }
    }

    fun prevListener() {
//        return binding.buttonActivityPrev.setOnClickListener{}
    }

    fun nextListener() {
//        return binding.buttonActivityNext.setOnClickListener {}
    }


}