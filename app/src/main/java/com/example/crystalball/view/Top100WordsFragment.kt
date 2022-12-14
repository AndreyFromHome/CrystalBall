package com.example.crystalball.view

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.crystalball.data.DictionaryItem
import com.example.crystalball.databinding.FragmentTop100WordsBinding
import com.example.crystalball.model.apis.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Top100WordsFragment : Fragment() {

    lateinit var binding: FragmentTop100WordsBinding

    lateinit var randomWord : String
    lateinit var getSoundUrl : String
    lateinit var getSoundAnotherUrl : String
    private lateinit var tvWord : TextView
    private lateinit var tvPhonetic : TextView
    lateinit var tvWordResult : TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTop100WordsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Начальные значение переменных и view
        binding.tvWord.text = ""
        binding.tvPhonetic.text = ""
        getSoundUrl = "https://ssl.gstatic.com/dictionary/static/sounds/20200429/hello--_gb_1.mp3"

        val listOfRandomWords = mutableListOf(
            "Inch",
            "Free",
            "Android",
            "Back",
            "Chat",
            "Crop",
            "Declare",
            "Glide",
            "Side",
            "Teach",
            "Modern",
            "Act",
            "Been",
            "Grow",
            "Neighbour",
            "Aunt",
            "Nephew",
            "Niece",
            "Shuffle"
        )

        // При нажатии на кнопку надо получить по API слово Android
        binding.btGetWord.setOnClickListener {
            listOfRandomWords.shuffle()
            randomWord = listOfRandomWords[0]
           //randomWord = listOfRandomWords.random()
           //getWordFromApi(randomWord)
            getWordFromApi(randomWord)
            Log.d("MyLog", "Жмём на кнопку и отправляем слово: $randomWord")
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = Top100WordsFragment()
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
                    val responseList = response?.body()!!
                    binding.tvWord.text = responseList[0].word

                  //  val phonetic1 =responseList[0].phonetics[0].text
                     binding.tvPhonetic.text = responseList[0].phonetics[0].text

                    val definition = responseList[0].meanings[0].definitions[0].definition
                    binding.tvDefinition.text = "Определение (definition): \n" + definition

                    try {
                        getSoundUrl = responseList[0].phonetics[0].audio
                        Log.d("MyLog", "Audio: $getSoundUrl")
                    }
                    catch (e: Exception) {
                        Log.d("MyLog", "Проблема, в getSoundUrl ничего нет")
                    }


                 //   playSound(getSoundUrl)

                        // Log.d("MyLog", "Audio: $getSoundAnotherUrl")
                    if (getSoundUrl != "") {
                        playSound(getSoundUrl)
                    } else if (getSoundUrl == "") {
                        try {
                            getSoundAnotherUrl = responseList[0].phonetics[1].audio
                            Log.d("MyLog", "Audio: $getSoundAnotherUrl")
                            playSound(getSoundAnotherUrl)
                        }
                        catch (e: Exception) {
                            Log.d("MyLog", "Проблема, в getSoundAnotherUrl ничего нет")
                        }

                    } else {
                        Log.d("MyLog", "Audio не найдено")
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
        //    Log.d("MyLog", "Слушаем слово $getSoundUrl")
        }
    }
}