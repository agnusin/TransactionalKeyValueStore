package ru.agnusin.core

import java.security.InvalidParameterException

interface Command<T, out R> {
    val label: String

    @Throws(InvalidParameterException::class)
    fun getAction(vararg args: String): Action<T, R>

    fun checkArguments(check: Boolean) {
        if (!check) throw IllegalArgumentException("wrong arguments")
    }
}