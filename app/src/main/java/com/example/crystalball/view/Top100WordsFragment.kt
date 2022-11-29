package com.example.crystalball.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.crystalball.R
import com.example.crystalball.databinding.FragmentTop100WordsBinding

class Top100WordsFragment : Fragment() {

    lateinit var binding: FragmentTop100WordsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTop100WordsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btAction.setOnClickListener {
            Log.d("MyLog", "Нажал кнопку btAction")
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = Top100WordsFragment()

    }
}