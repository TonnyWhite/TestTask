package cache

/**
 * A generic cache interface.
 */
interface Cache<K, V> {


     val entriesSize: Int


     val maxSizeEntries: Int

    /**
     * Returns the value corresponding to the given [key], or `null` if there's no value associated with this [key].
     */
     operator fun get(key: K): V?

    /**
     * Associates the specified [key] with the specified [value] in the cache.
     */
     operator fun set(key: K, value: V)

    /**
     * Removes the specified key and its corresponding value from this cache.
     *
     * @return true if the key was present in the cache, or `null` if there's no value associated with this [key].
     */
     fun remove(key: K): Boolean

    /**
     * Removes all the entries in the cache.
     */
     fun clear()

}