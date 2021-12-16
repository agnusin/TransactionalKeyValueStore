package ru.agnusin.store.core

abstract class Action<T, out R>(val args: List<String>) {

    abstract fun exec(t: T): Result<R>

    sealed class Result<out T> {
        data class Success<T>(val data: T): Result<T>()
        data class Error<T>(val msg: String): Result<T>()
    }

    companion object {

        fun <R>checkArgumentsAndRun(check: Boolean, run: () -> Result<R>): Result<R> {
            return if (!check) Result.Error("wrong number of arguments")
            else run()
        }
    }
}