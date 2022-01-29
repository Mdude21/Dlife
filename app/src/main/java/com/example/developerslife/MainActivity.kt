package com.example.developerslife

import android.location.GnssAntennaInfo
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.developerslife.databinding.ActivityMainBinding
import com.example.developerslife.ui.main.*
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var pageAdapter: SectionsPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        with(binding) {
            buttonActivityNext?.setOnClickListener {
                pageAdapter.fragments.forEach {
                    if (it.isVisible) (it as ButtonClickListener).next()
                }
            }
            buttonActivityPrev?.setOnClickListener {
                pageAdapter.fragments.forEach {
                    if (it.isVisible) (it as ButtonClickListener).prev()
                }
            }
        }

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