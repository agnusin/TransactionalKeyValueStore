package ru.agnusin.commands

import ru.agnusin.store.Store

interface Action<out T> {

    fun invoke(store: Store): Result<T>

    sealed class Result<out T> {
        data class Success<T>(val data: T): Result<T>()
        data class Error<T>(val msg: String): Result<T>()
    }
}