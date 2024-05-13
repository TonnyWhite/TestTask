package theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class ZimranColors(
    val primaryBackground: Color,
    val primaryActionColor: Color,
    val primaryTextColor: Color,
    val hintTextColor: Color,
    val highLightTextColor: Color,
    val secondaryTextColor: Color,
    val thirdTextColor: Color,
    val tagColor: Color,
    val tagTextColor:Color
)


val palette = ZimranColors(
    primaryBackground = Color(0xFF050B18),
    primaryActionColor = Color(0xFFF4D144),
    primaryTextColor = Color(0xFF050B18),
    hintTextColor = Color(0xFF696C75),
    highLightTextColor = Color(0xFfFF4D144),
    secondaryTextColor = Color(0xFFFFFFFF),
    thirdTextColor = Color(0XFFEEF2FB),
    tagColor = Color(0x1844A9F4),
    tagTextColor = Color(0xFF44A9F4)

)

val LocalColorProvider = staticCompositionLocalOf<ZimranColors> {
    error("No default implementation")
}