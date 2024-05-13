package widgets

import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import LoadImage
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp


@Composable
@NonRestartableComposable
fun MovieListPoster(
    posterUrl: String,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Fit
) {
    LoadImage(
        url = posterUrl,
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = contentScale,
        onLoading = { CircularProgressIndicator(it) },
    )
}


@Composable
fun MovieDetailsBackDrop(
    backDropUrl: String,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.FillWidth
) {
    LoadImage(
        url = backDropUrl,
        contentDescription = contentDescription,
        modifier = modifier
            .height(200.dp),
        contentScale = contentScale,
        onLoading = { LinearProgressIndicator(it) },
        onFailure = { Toast.makeText(LocalContext.current, it.message.toString(), Toast.LENGTH_LONG).show()}
    )
}






