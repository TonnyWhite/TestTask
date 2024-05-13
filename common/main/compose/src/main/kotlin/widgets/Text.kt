package widgets

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import theme.Theme


@Composable
fun Headline(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title,
        modifier = modifier,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun MovieListTitle(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title,
        modifier = modifier,

        fontWeight = FontWeight.Bold
    )
}

@Composable
fun MovieListDescription(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title,
        modifier = modifier,
        fontWeight = FontWeight.Normal,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun MovieDetailsTitle(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title,
        color = Theme.colors.secondaryTextColor,
        modifier = modifier,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun MovieDetailsDescription(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title,
        color = Theme.colors.secondaryTextColor,
        modifier = modifier,
        fontWeight = FontWeight.Normal
    )
}

@Composable
fun MovieDetailsReleaseDate(releaseDate: String, modifier: Modifier = Modifier) {
    Text(
        text =  releaseDate,
        modifier = modifier,
        fontWeight = FontWeight.Thin
    )
}

@Composable
fun InfoText(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        modifier = modifier
            .padding(12.dp),
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Medium,
    )
}
