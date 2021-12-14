package ru.agnusin.core

interface Action<T, out R> {

    fun exec(t: T): Result<R>

    sealed class Result<out T> {
        data class Success<T>(val data: T): Result<T>()
        data class Error<T>(val msg: String): Result<T>()
    }
}