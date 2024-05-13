
import AsynchronousResourceLoading.*


sealed interface AsynchronousResourceLoading<out T> {

    /**
     * Source from where data has been loaded.
     */
     val source: DataSource

    /**
     * Represents the resource is still in the loading state.
     */
     data class Loading(
        val progress: Float,
        override val source: DataSource = DataSource.NONE,
    ) : AsynchronousResourceLoading<Nothing>

    /**
     * Represents the resource as a successful outcome.
     */
     data class Success<out T>(
        val value: T,
        override val source: DataSource = DataSource.NONE,
    ) : AsynchronousResourceLoading<T>

    /**
     * Represents the resource as a failure outcome.
     */
     data class Failure(
        val exception: Throwable,
        override val source: DataSource = DataSource.NONE,
    ) : AsynchronousResourceLoading<Nothing>
}


/**
 * Returns [Success] with the [transform] function applied on the value if the resource represents success.
 * or [Failure] with the original exception if the resource represents failure.
 */
 inline fun <T, R> AsynchronousResourceLoading<T>.map(transform: (T) -> R): AsynchronousResourceLoading<R> = when (this) {
    is Loading -> Loading(progress, source)
    is Success -> Success(transform(value), source)
    is Failure -> Failure(exception, source)
}

