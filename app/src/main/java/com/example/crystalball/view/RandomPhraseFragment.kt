package com.example.crystalball.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.crystalball.R

class RandomPhraseFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_random_phrase, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance() = RandomPhraseFragment()

    }
}