package ru.agnusin.store.core

interface Store {

    fun put(e: Entry)

    fun get(k: String): Entry?

    fun remove(k: String): Entry?

    fun values(): Set<Entry>

    fun copy(): Store

    class Entry (
        val key: String,
        val value: String
    )
}