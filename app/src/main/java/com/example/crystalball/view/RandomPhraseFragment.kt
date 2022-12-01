package com.example.crystalball.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.crystalball.data.wordsList
import com.example.crystalball.databinding.FragmentRandomPhraseBinding

class RandomPhraseFragment : Fragment() {

    lateinit var binding: FragmentRandomPhraseBinding
    var indexNumber = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRandomPhraseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btNextPhrase.setOnClickListener {
            indexNumber = randomNumber()
            binding.tvPhrase.text = getWord()
            binding.tvSentence.text = getSentence()
            Log.d("MyLog", "btNextPhrase")
        }
    }

    private fun randomNumber(): Int {
        val size = wordsList.size - 1
            // Log.d("MyLog", "${wordsList[0].words + wordsList[0].sentence}")
        return (0..size).random()
    }

    private fun getWord(): String {
        return wordsList[indexNumber].words
    }

    private fun getSentence(): String {
        return wordsList[indexNumber].sentence
    }

    companion object {
        @JvmStatic
        fun newInstance() = RandomPhraseFragment()
    }
}