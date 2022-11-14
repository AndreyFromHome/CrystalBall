package com.example.crystalball

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.crystalball.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    var indexNumber = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.mainLayout.setOnClickListener {
            binding.tvReto.text = getWord()
            binding.tvReto2.text = getSentence()
            indexNumber = randomNumber()
        }

    }
    private fun randomNumber(): Int {
        val size = wordsList.size - 1
        //       Log.d("MyLog", "${wordsList[0].words +wordsList[0].sentence}")
        return (0..size).random()

    }

    private fun getWord(): String {
        return wordsList[indexNumber].words
    }

    private fun getSentence(): String {
        return wordsList[indexNumber].sentence
    }



}