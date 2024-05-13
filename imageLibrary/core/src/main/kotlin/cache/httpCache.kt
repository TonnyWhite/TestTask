package cache

import applicationContext
import disk.DiskCacheStorage
import io.ktor.client.plugins.cache.storage.CacheStorage
import okio.FileSystem
import okio.Path.Companion.toOkioPath

private val cacheDir = applicationContext.cacheDir.toOkioPath()

fun httpCacheStorage(maxSize: Long): CacheStorage = DiskCacheStorage(
    fileSystem = FileSystem.SYSTEM,
    directory = cacheDir,
    maxSize = maxSize
)