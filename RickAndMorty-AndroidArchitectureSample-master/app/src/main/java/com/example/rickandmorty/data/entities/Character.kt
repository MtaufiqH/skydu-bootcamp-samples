package com.example.rickandmorty.data.entities


data class Character(
    val id: Int,
    val gender: String,
    val image: String,
    val name: String,
    val species: String,
    val status: String
)