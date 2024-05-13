import io.ktor.http.*

/**
 * Represents the source from where data has been loaded.
 */
enum class DataSource {

    /**
     * Represents an in-memory data source (e.g. [Cache]).
     */
    MEMORY_CACHE,

    /**
     * Represents a disk data source (e.g. [File]).
     */
    DISK,

    /**
     * Represents a network data source (e.g. [Url]).
     */
    NETWORK,

    /**
     * Represents no data source.
     */
    NONE,
}