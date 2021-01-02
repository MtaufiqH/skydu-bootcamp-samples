package com.example.rickandmorty.ui.characterdetail

import androidx.lifecycle.*
import com.example.rickandmorty.data.entities.Character
import com.example.rickandmorty.data.repository.CharacterRepository
import com.example.rickandmorty.utils.Result
import kotlinx.coroutines.launch

class CharacterDetailViewModel(private val characterId: Int): ViewModel() {
    private val repository = CharacterRepository()

    private val loadTrigger = MutableLiveData(Unit)

    init {
        viewModelScope.launch {
            refresh()
        }
    }

    val character: LiveData<Result<Character>> = loadTrigger.switchMap {
        repository.getCharacter(characterId)
    }

    fun refresh() {
        loadTrigger.value = Unit
    }
}
