package com.example.crystalball

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.VpnService.prepare
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.example.crystalball.data.Dictionary
import com.example.crystalball.data.DictionaryItem
import com.example.crystalball.databinding.ActivityMainBinding
import com.example.crystalball.model.apis.ApiInterface
import kotlinx.coroutines.NonCancellable.start
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    var indexNumber = 0
    private lateinit var tvWord : TextView
    private lateinit var tvPhonetic : TextView
    lateinit var tvWordResult : TextView
    lateinit var btGetWord : Button

    lateinit var getWord : String
    lateinit var getSoundUrl : String

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.tvWord.text = "Ожидаем данные"

        binding.mainLayout.setOnClickListener {
            binding.tvReto.text = getWord()
            binding.tvReto2.text = getSentence()
            indexNumber = randomNumber()


        }

        var listOfRandomWords = listOf(
            "Apple",
            "Android",
            "Hello",
            "Declare",
            "Shuffle"
        )

        getSoundUrl = "https://ssl.gstatic.com/dictionary/static/sounds/20200429/hello--_gb_1.mp3"

        // При нажатии на кнопку надо получить по API слово Android
        binding.btGetWord.setOnClickListener {

            getWord = listOfRandomWords.random()
            getWordFromApi()

        }





     //   var listOfRandomWordsSize = listOfRandomWords.size - 1
     //   var listOfRandomWordsRandom = (0..listOfRandomWordsSize).random()

        // binding.tvPhonetic.text = listOfRandomWords.get(3)

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

    private fun getWordFromApi() {
        val apiInterface = ApiInterface.create().getDictionaryItem(getWord)
        apiInterface.enqueue(object : Callback<List<DictionaryItem>> {
            override fun onResponse(
                call: Call<List<DictionaryItem>>?,
                response: Response<List<DictionaryItem>>?
            ) {
//                Log.d("MyLog", "Ответ от API: $response")
                if (response?.body() != null) {
                    val responseList = response?.body()
                    responseList?.forEach { it ->
                        //   Log.d("MyLog", "${it.word}")
                        binding.tvWord.text = it.word + getSoundUrl
                        binding.tvPhonetic.text = it.phonetic
                        it.phonetics.forEach {
                            getSoundUrl = it.audio
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<DictionaryItem>>?, t: Throwable?) {
                // do nothing
                // Log.d("MyLog", "Ошибка: $t")
                binding.tvWord.text = "Что-то не так"
            }
        })

        val url = getSoundUrl // your URL here
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
        }
    }


}