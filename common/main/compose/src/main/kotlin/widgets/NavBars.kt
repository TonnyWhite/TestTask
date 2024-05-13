package widgets


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import theme.Theme.colors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(onBackPressed: (() -> Unit)? = null) {
    TopAppBar(
        title = { Text("Details") },
        navigationIcon = {
            BackArrowIcon(
                onClick = { onBackPressed?.invoke() }
            )
        },
        colors = TopAppBarColors(
            colors.primaryBackground,
            colors.primaryBackground,
            colors.highLightTextColor,
            colors.highLightTextColor,
            colors.tagColor
        )

    )
}

@Composable
fun BackArrowIcon(onClick: () -> Unit?) {
    IconButton(
        onClick = { onClick() },
        content = {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back Arrow"
            )
        }
    )
}

