package com.example.rickandmorty.ui.characters

import androidx.lifecycle.*
import com.example.rickandmorty.data.entities.Character
import com.example.rickandmorty.data.repository.CharacterRepository
import com.example.rickandmorty.utils.Result

class CharactersViewModel constructor() : ViewModel() {

    private val repository: CharacterRepository = CharacterRepository()

    private val loadTrigger = MutableLiveData<Int>()

    init {
        refresh()
    }


    val characters: LiveData<Result<List<Character>>> = loadTrigger.switchMap {
        if(it == 2) {
            liveData {
                emit(Result.success(emptyList()))
            }
        } else {
            repository.getCharacters(it % 2 == 0)
        }
    }

    fun refresh() {
        loadTrigger.value = 0
    }

    fun refreshError() {
        loadTrigger.value = 1
    }

    fun clear() {
        loadTrigger.value = 2
    }

}
