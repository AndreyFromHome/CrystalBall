package com.example.crystalball.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.crystalball.databinding.ActivityMainBinding
import com.example.crystalball.view.adapters.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    private val fragList = listOf(
        RandomPhraseFragment.newInstance(),
        Top100WordsFragment.newInstance()
    )

    private val fragListTitles = listOf(
        "Случайная фраза",
        "ТОП-100 слов"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val vpAdapter = ViewPagerAdapter(this, fragList)
        binding.vp2.adapter = vpAdapter // устанавливаем адаптер в наш viewPager
        TabLayoutMediator(binding.upTabLayout, binding.vp2) { // связываем tabLayout и viewPager
            tab, pos -> tab.text = fragListTitles[pos]
        }.attach()

    }

}