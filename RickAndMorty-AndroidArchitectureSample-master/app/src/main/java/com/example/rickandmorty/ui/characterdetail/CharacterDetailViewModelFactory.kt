package com.example.rickandmorty.ui.characterdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class CharacterDetailViewModelFactory(val characterId: Int) : ViewModelProvider.Factory    {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CharacterDetailViewModel(characterId) as T
    }
}