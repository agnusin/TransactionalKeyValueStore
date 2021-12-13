package ru.agnusin.store

class HashMapStore(): Store {

    private val map = hashMapOf<String, String>()

    override val size: Int = map.size

    override fun containsKey(key: String): Boolean = map.containsKey(key)

    override fun containsValue(value: String): Boolean = containsValue(value)

    override fun get(key: String): String? = map[key]

    override fun isEmpty(): Boolean = map.isEmpty()

    override val entries: MutableSet<MutableMap.MutableEntry<String, String>> = map.entries

    override val keys: MutableSet<String> = map.keys

    override val values: MutableCollection<String> = map.values

    override fun clear() = map.clear()

    override fun put(key: String, value: String): String? = map.put(key, value)

    override fun putAll(from: Map<out String, String>) = map.putAll(from)

    override fun remove(key: String): String? = map.remove(key)
}