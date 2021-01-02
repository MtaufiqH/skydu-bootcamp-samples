package com.example.rickandmorty.data.remote

import com.example.rickandmorty.data.DataResult
import retrofit2.Response
import timber.log.Timber


class CharacterRemoteDataSource constructor(
    private val characterService: CharacterService
) {

    suspend fun getCharacters() = getResult(characterService.getAllCharacters())
    suspend fun getCharacter(id: Int) = getResult(characterService.getCharacter(id))

    private fun <T> getResult(response: Response<T>): DataResult<T> {
        try {
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return DataResult(true, body)
            }
            return error(" ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): DataResult<T> {
        Timber.d(message)
        return DataResult(false, null,"Network call has failed for a following reason: $message")
    }
}