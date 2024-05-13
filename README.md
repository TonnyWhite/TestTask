# Main Components LoadImage: A Composable function designed for loading and displaying images.

Parameters:
#### url: String - URL for downloading the image.
#### contentDescription: String? - Description for accessibility.
#### modifier: Modifier - Modifier for configuring the appearance and layout of the image.
#### contentScale: ContentScale - Content scaling of the image.
#### onLoading: @Composable (Float) -> Unit - Animation or progress indicator during loading.
#### onFailure: (Throwable) -> Unit - Error handling for failed image loading.

### @Composable
# fun LoadImage(
    url: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    onLoading: @Composable (Float) -> Unit = { LinearProgressIndicator(it) },
    onFailure: (Throwable) -> Unit = { Toast.makeText(LocalContext.current, it.message.toString(), Toast.LENGTH_LONG).show() }
) 
asyncPainterResource: A function for asynchronous image loading.

Loads images using coroutine context (Dispatchers.IO).
Supports caching based on LruCache.
Supports Bitmap, SVG, and XML formats.
ImageLoaderConfigBuilder: A class for configuring image loading parameters.

Allows configuration of loading parameters such as cache size, loading priority, and other options.
Technologies Used
Kotlin Coroutines: For asynchronous image loading.
Dispatchers.IO: For performing I/O operations in a separate context.
LruCache: For in-memory image caching.
Ktor: For performing network requests.
Usage Example
To use the library, you need to include the imageLibrary:image-compose module and call the LoadImage function:


### @Composable
# fun ExampleUsage() {
     LoadImage(
        url = "https://example.com/image.png",
        contentDescription = "Example Image",
        modifier = Modifier.height(200.dp),
        contentScale = ContentScale.Crop,
        onLoading = { LinearProgressIndicator(it) },
        onFailure = { Toast.makeText(LocalContext.current, it.message.toString(), Toast.LENGTH_LONG).show()}
    )
}
Future Improvements
Writing Tests: Implement tests to ensure the correct functionality of the library.
Request Cancellation: Add functionality to cancel image loading requests when necessary (e.g., when exiting a screen).
Experience and Insights
Working on the module was interesting and informative. It was useful to explore the internal workings of libraries like Glide and Coil and understand the principles of LruCache. The library provides flexible options for loading and displaying images in modern Android applications using Jetpack Compose.
