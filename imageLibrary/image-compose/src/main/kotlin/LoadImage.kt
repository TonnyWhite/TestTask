import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale

/**
 * @param resource The [AsynchronousResourceLoading] that needs to be displayed.
 * @param modifier The modifier that is applied to the [Box].
 * @param onLoading Composable which is used while the image is in [AsynchronousResourceLoading.Loading] state.
 * @param onFailure Composable which is used while the image is in [AsynchronousResourceLoading.Failure] state.
 * @param contentAlignment The default alignment inside the Box.
 * @param animationSpec a [FiniteAnimationSpec] to be used in [Crossfade] animation, or null to be disabled.
 * The rest is the default [Image] parameters.
 */

@NonRestartableComposable
@Composable
fun LoadImage(
    url: Any,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
    onLoading: @Composable (BoxScope.(Float) -> Unit)? = null,
    onFailure: @Composable (BoxScope.(Throwable) -> Unit)? = null,
    contentAlignment: Alignment = Alignment.Center,
    animationSpec: FiniteAnimationSpec<Float>? = null,

) {

    val painterAsynchronousResourceLoading: AsynchronousResourceLoading<Painter> =
        asyncPainterResource(
            url,
            filterQuality = FilterQuality.High,
        )

    val onSuccess: @Composable (BoxScope.(Painter) -> Unit) = { painter ->
        Image(
            painter,
            contentDescription,
            Modifier.fillMaxSize(),
            alignment,
            contentScale,
            alpha,
            colorFilter
        )
    }
    ZimranImageBox(
        painterAsynchronousResourceLoading,
        modifier,
        contentAlignment,
        animationSpec,
        onLoading,
        onFailure,
        onSuccess,
    )
}

@Composable
fun LoadImage(
    asynchronousResourceLoading: AsynchronousResourceLoading<Painter>,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
    onLoading: @Composable (BoxScope.(Float) -> Unit)? = null,
    onFailure: @Composable (BoxScope.(Throwable) -> Unit)? = null,
    contentAlignment: Alignment = Alignment.Center,
    animationSpec: FiniteAnimationSpec<Float>? = null,
) {
    val onSuccess: @Composable (BoxScope.(Painter) -> Unit) = { painter ->
        Image(
            painter,
            contentDescription,
            Modifier.fillMaxSize(),
            alignment,
            contentScale,
            alpha,
            colorFilter
        )
    }
    ZimranImageBox(
        asynchronousResourceLoading,
        modifier,
        contentAlignment,
        animationSpec,
        onLoading,
        onFailure,
        onSuccess,
    )
}

/**
 * A composable that is used to display a [Painter] resource.
 * To load an image [AsynchronousResourceLoading] asynchronously, use [asyncPainterResource].
 * @param asynchronousResourceLoading The [AsynchronousResourceLoading] that needs to be displayed.
 * @param modifier The modifier that is applied to the [Box].
 * @param contentAlignment The default alignment inside the Box.
 * @param animationSpec a [FiniteAnimationSpec] to be used in [Crossfade] animation, or null to be disabled.
 * @param onLoading Composable which is used while the image is in [AsynchronousResourceLoading.Loading] state.
 * @param onFailure Composable which is used while the image is in [AsynchronousResourceLoading.Failure] state.
 * @param onSuccess Composable which is used while the image is in [AsynchronousResourceLoading.Success] state.
 */

@Composable
fun ZimranImageBox(
    asynchronousResourceLoading: AsynchronousResourceLoading<Painter>,
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.Center,
    animationSpec: FiniteAnimationSpec<Float>? = null,
    onLoading: @Composable (BoxScope.(Float) -> Unit)? = null,
    onFailure: @Composable (BoxScope.(Throwable) -> Unit)? = null,
    onSuccess: @Composable BoxScope.(Painter) -> Unit,
) {
    Box(modifier, contentAlignment) {
        if (animationSpec != null) {
            Crossfade(asynchronousResourceLoading, animationSpec = animationSpec) { animatedResource ->
                when (animatedResource) {
                    is AsynchronousResourceLoading.Loading -> if (onLoading != null) onLoading(animatedResource.progress)
                    is AsynchronousResourceLoading.Success -> onSuccess(animatedResource.value)
                    is AsynchronousResourceLoading.Failure -> if (onFailure != null) onFailure(animatedResource.exception)
                }
            }
        } else {
            when (asynchronousResourceLoading) {
                is AsynchronousResourceLoading.Loading -> if (onLoading != null) onLoading(asynchronousResourceLoading.progress)
                is AsynchronousResourceLoading.Success -> onSuccess(asynchronousResourceLoading.value)
                is AsynchronousResourceLoading.Failure -> if (onFailure != null) onFailure(asynchronousResourceLoading.exception)
            }
        }
    }
}