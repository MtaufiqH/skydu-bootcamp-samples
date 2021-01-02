package com.example.rickandmorty.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.rickandmorty.data.entities.Character
import com.example.rickandmorty.data.local.CharacterDataHolder
import com.example.rickandmorty.data.remote.CharacterRemoteDataSource
import com.example.rickandmorty.data.remote.CharacterService
import com.example.rickandmorty.utils.Result
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CharacterRepository {
    private val remoteDataSource: CharacterRemoteDataSource
    private val localDataSource: CharacterDataHolder

    companion object {
        val service: CharacterService = Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CharacterService::class.java)
    }

    init {
        remoteDataSource = CharacterRemoteDataSource(
            service
        )
        localDataSource = CharacterDataHolder
    }

    fun getCharacter(id: Int): LiveData<Result<Character>> {
        return liveData(Dispatchers.IO) {
            emit(Result.loading())
            val response = remoteDataSource.getCharacter(id)
            if (response.success) {
                emit(Result.success(response.data!!))
            } else {
                emit(Result.error(response.errorMessage!!, null))
            }
        }
    }

    fun getCharacters(success: Boolean): LiveData<Result<List<Character>>> {
        return liveData(Dispatchers.IO) {
            emit(Result.loading())
            val cache = localDataSource.getCharacters()
            emit(Result.cache(cache))

            if(success) {
                val response = remoteDataSource.getCharacters()
                if (response.success) {
                    localDataSource.insertAll(response.data?.results?: emptyList())
                    emit(Result.success(response.data?.results?: emptyList()))
                } else {
                    emit(Result.error(response.errorMessage!!, cache))
                }
            } else {
                emit(Result.error("Error euy", cache))
            }
        }

    }
}