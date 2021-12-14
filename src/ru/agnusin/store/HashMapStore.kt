package ru.agnusin.store

import ru.agnusin.core.Store

class HashMapStore: Store {

    private val map = hashMapOf<String, String>()

    override fun put(k: String, v: String) {
        map[k] = v
    }

    override fun get(k: String): String? = map[k]

    override fun remove(k: String): String? = map.remove(k)

    override fun values(): List<String> = map.values.toList()

    override fun copy(): Store {
        val store = HashMapStore()
        for (entry in map.entries) {
            store.put(entry.key, entry.value)
        }
        return store
    }
}