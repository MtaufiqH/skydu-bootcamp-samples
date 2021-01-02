package com.example.rickandmorty.data.local

import com.example.rickandmorty.data.entities.Character

object CharacterDataHolder {
    val cache: ArrayList<Character> = arrayListOf()

    fun getCharacter(id: Int): Character? {
        return cache.find {
            it.id == id
        }
    }

    fun getCharacters(): List<Character> {
        return cache
    }

    fun insertAll(param: List<Character>) {
        cache.clear()
        cache.addAll(param)
    }

    fun insert(param: Character) {
        val idx = cache.indexOfFirst {
            it.id == param.id
        }

        if (idx != -1) {
            cache.set(idx, param)
        } else {
            cache.add(param)
        }
    }


}