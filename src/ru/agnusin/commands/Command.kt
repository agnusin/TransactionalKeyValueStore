package ru.agnusin.commands

interface Command<out T> {
    val label: String

    @Throws(IllegalArgumentException::class)
    fun getAction(str: String): Action<T>

    fun splitArguments(str: String): List<String> =
        str.split(" ")
}