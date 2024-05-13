package utils

import cache.Cache

/**
 * Cache implementation which evicts items using an LRU algorithm.
 */
internal class LruCache<K : Any, V : Any>(override val maxSizeEntries: Int) : Cache<K, V> {

    private val cache: io.github.reactivecircus.cache4k.Cache<K, V> =
        io.github.reactivecircus.cache4k.Cache.Builder<K, V>()
            .maximumCacheSize(maxSizeEntries.toLong())
            .build()

    override val entriesSize: Int
        get() = cache.asMap().size

    init {
        require(maxSizeEntries >= 0) { "Cache max size must be positive number" }
    }

    override fun get(key: K): V? = cache.get(key)

    override fun set(key: K, value: V) = cache.put(key, value)

    override fun remove(key: K): Boolean {
        cache.invalidate(key)
        return true
    }

    override fun clear(): Unit = cache.invalidateAll()
}