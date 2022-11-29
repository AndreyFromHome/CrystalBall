package com.example.crystalball.data

data class EnglishWords(val words: String, val sentence: String)

val wordsList = listOf<EnglishWords>(
    EnglishWords("Boil the kettle", "Boil the kettle please - боил зэ кэтл плис"),
    EnglishWords("Where can I get..?", "Where can I get hot water? - вэа кэн ай гэт хот вотэ"),
    EnglishWords("Give me 2 forks", "Give me 2 forks and 2 spoons please - гив ми ту фоокс энд спуунс плис"),
)
