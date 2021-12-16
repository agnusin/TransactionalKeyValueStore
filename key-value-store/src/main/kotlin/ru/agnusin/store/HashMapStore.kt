package ru.agnusin.store

import ru.agnusin.store.core.Store

class HashMapStore: Store {

    private val map = hashMapOf<String, String>()

    override fun put(e: Store.Entry) {
        map[e.key] = e.value
    }

    override fun get(k: String): Store.Entry? {
        return map[k]?.let { Store.Entry(k, it) }
    }

    override fun remove(k: String): Store.Entry? {
        return map.remove(k)?.let { Store.Entry(k, it) }
    }

    override fun values(): Set<Store.Entry> {
        return map.entries.map { Store.Entry(it.key, it.value) }.toSet()
    }

    override fun copy(): Store {
        val store = HashMapStore()
        for (entry in map.entries) {
            val e = Store.Entry(entry.key, entry.value)
            store.put(e)
        }
        return store
    }
}