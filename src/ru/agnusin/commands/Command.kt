package ru.agnusin.commands

import java.security.InvalidParameterException

interface Command<out T> {
    val label: String

    @Throws(InvalidParameterException::class)
    fun getAction(vararg args: String): Action<T>

    fun checkArguments(check: Boolean) {
        if (!check) throw IllegalArgumentException("wrong arguments")
    }
}