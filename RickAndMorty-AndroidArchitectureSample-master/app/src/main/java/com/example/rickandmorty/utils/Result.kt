package com.example.rickandmorty.utils

data class Result<out T>(val status: Status, val data: T?, val message: String?) {

    enum class Status {
        LOADING,
        CACHE,
        SUCCESS,
        ERROR,
    }

    companion object {
        fun <T> cache(data: T): Result<T> {
            return Result(Status.CACHE, data, null)
        }

        fun <T> success(data: T): Result<T> {
            return Result(Status.SUCCESS, data, null)
        }

        fun <T> error(message: String, cacheData: T?): Result<T> {
            return Result(Status.ERROR, cacheData, message)
        }

        fun <T> loading(): Result<T> {
            return Result(Status.LOADING, null, null)
        }
    }
}