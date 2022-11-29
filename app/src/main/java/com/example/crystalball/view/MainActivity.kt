package com.example.crystalball.view

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.crystalball.R
import com.example.crystalball.data.DictionaryItem
import com.example.crystalball.data.wordsList
import com.example.crystalball.databinding.ActivityMainBinding
import com.example.crystalball.model.apis.ApiInterface
import com.example.crystalball.view.adapters.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val fragList = listOf(
        RandomPhraseFragment.newInstance(),
        Top100WordsFragment.newInstance()
    )

    private val fragListTitles = listOf(
        "Случайная фраза",
        "ТОП-100 слов"
    )

    lateinit var binding : ActivityMainBinding
    var indexNumber = 0
    private lateinit var tvWord : TextView
    private lateinit var tvPhonetic : TextView
    lateinit var tvWordResult : TextView

    lateinit var randomWord : String
    lateinit var getSoundUrl : String

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.tvWord.text = ""
        binding.tvPhonetic.text = ""

        val vpAdapter = ViewPagerAdapter(this, fragList)
        binding.vp2.adapter = vpAdapter // устанавливаем адаптер в наш viewPager
        TabLayoutMediator(binding.upTabLayout, binding.vp2) { // связываем tabLayout и viewPager
            tab, pos -> tab.text = fragListTitles[pos]
        }.attach()

        binding.mainLayout.setOnClickListener {
            binding.tvReto.text = getWord()
            binding.tvReto2.text = getSentence()
            indexNumber = randomNumber()

        }

        var listOfRandomWords = listOf(
            "Inch",
            "Apple",
            "Android",
            "Chat",
            "Hello",
            "Declare",
            "Shuffle"
        )

        getSoundUrl = "https://ssl.gstatic.com/dictionary/static/sounds/20200429/hello--_gb_1.mp3"


        // При нажатии на кнопку надо получить по API слово Android
        binding.btGetWord.setOnClickListener {

            randomWord = listOfRandomWords.random()
            getWordFromApi(randomWord)
            Log.d("MyLog", "Жмём на кнопку и отправляем слово: $randomWord")

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

    // Получение полной информации о слове с API https://dictionaryapi.dev/
    // Случайные слова для подставноки в API берутся из локальной БД

    private fun getWordFromApi(getWord: String) {
        val apiInterface = ApiInterface.create().getDictionaryItem(getWord)
        apiInterface.enqueue(object : Callback<List<DictionaryItem>> {
            override fun onResponse(
                call: Call<List<DictionaryItem>>?,
                response: Response<List<DictionaryItem>>?
            ) {

                if (response?.body() != null) {
                    val responseList = response?.body()
                    responseList?.forEach { it ->
                        //   Log.d("MyLog", "${it.word}")
                        getSoundUrl = it.phonetics[0].audio

                        playSound(getSoundUrl)

                        binding.tvWord.text = it.word
                        binding.tvPhonetic.text = it.phonetic


                        Log.d("MyLog", "Получена ссылка $getSoundUrl \n Получено слово: ${it.word}")
/*                            .forEach {
                            getSoundUrl = it.audio.
                            Log.d("MyLog", "Получена ссылка $getSoundUrl")
                        }*/
                    }
                }
            }

            override fun onFailure(call: Call<List<DictionaryItem>>?, t: Throwable?) {
                // do nothing
                // Log.d("MyLog", "Ошибка: $t")
                binding.tvWord.text = "Что-то не так"
            }
        })


    }

    fun playSound(setUrlForPlayer: String) {
        val url = setUrlForPlayer // your URL here
        val mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            setDataSource(url)
            prepare() // might take long! (for buffering, etc)
            start()
            Log.d("MyLog", "Слушаем слово $getSoundUrl")
        }
    }

}