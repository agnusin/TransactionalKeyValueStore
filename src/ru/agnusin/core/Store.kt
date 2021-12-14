package ru.agnusin.core

interface Store {

    fun put(k: String, v: String)

    fun get(k: String): String?

    fun remove(k: String): String?

    fun values(): List<String>

    fun copy(): Store
}