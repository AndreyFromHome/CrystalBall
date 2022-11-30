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

        var listOfRandomWords = listOf(
            "Inch",
            "Apple",
            "Android",
            "Chat",
            "Hello",
            "Declare",
            "Shuffle"
        )

        // При нажатии на кнопку надо получить по API слово Android
        binding.btGetWord.setOnClickListener {
            randomWord = listOfRandomWords.random()
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
                    val responseList = response?.body()
                    responseList?.forEach { it ->
                        //   Log.d("MyLog", "${it.word}")
                        getSoundUrl = it.phonetics[0].audio

                        playSound(getSoundUrl)

                        binding.tvWord.text = it.word
                        binding.tvPhonetic.text = it.phonetic
/*                        it.meanings.forEach{
                            it.definitions.forEach{
                                it.definition
                            }
                        }*/
                        val definition = it.meanings[0].definitions[0].definition
                        definition?.let {
                            binding.tvDefinition.text = "Определение (definition): \n" + definition
                        }

                        Log.d("MyLog", "Получена ссылка $getSoundUrl \n Получено слово: ${it.word}")

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