package com.example.developerslife

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
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

//        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
//        val viewPager: ViewPager = binding.viewPager
//        viewPager.adapter = sectionsPagerAdapter
//        val tabs: TabLayout = binding.tabs
//        tabs.setupWithViewPager(viewPager)
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


    }
}