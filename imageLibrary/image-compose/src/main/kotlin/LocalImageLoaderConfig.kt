import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import config.Default
import config.ImageLoaderConfig
import config.ImageLoaderConfigBuilder

/**
 * Static CompositionLocal that provides the default configuration of [ImageLoaderConfigBuilder].
 */
val LocalImageLoaderConfig: ProvidableCompositionLocal<ImageLoaderConfig> = staticCompositionLocalOf { ImageLoaderConfig.Default }