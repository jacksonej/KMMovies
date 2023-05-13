import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.jackz.kmmovies.Greeting
import com.jackz.kmmovies.Movie
import com.jackz.kmmovies.repository.MovieRepository
import io.kamel.core.Resource
import io.kamel.image.KamelImage
import io.kamel.image.lazyPainterResource
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.imageio.ImageIO


@ExperimentalFoundationApi
fun main() = application {
    val windowState = rememberWindowState()

    Window(
        onCloseRequest = ::exitApplication,
        title = "Compose for Desktop",
        state = windowState
    ) {
        var movieList by remember { mutableStateOf(emptyList<Movie>()) }

        MaterialTheme {
            LaunchedEffect(true) {
                MovieRepository().getMovies {
                    movieList = it.results
                }

            }
            Column {
                TopAppBar(
                    title = {
                        Text(text = Greeting().greeting())
                    },
                    elevation = 2.dp
                )
                Spacer(modifier = Modifier.height(16.dp))
                LazyVerticalGrid(columns = GridCells.Fixed(3), modifier = Modifier.fillMaxSize()) {
                    items(movieList) { movies ->
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            SampleImage(
                                movie = movies,
                                modifier = Modifier.fillMaxSize(),
                            )
                            Text(text = movies.title, maxLines = 1, fontSize = 17.sp)
                        }
                    }
                }
            }
        }
    }





}

@Composable
public fun SampleImage(movie :Movie, modifier: Modifier = Modifier) {

    val imageUrl: String = remember { ("https://image.tmdb.org/t/p/w185"+movie.posterPath) }

    when (val imageResource = lazyPainterResource(imageUrl)) {
        is Resource.Loading -> {
            CircularProgressIndicator()
        }
        is Resource.Success -> {
            KamelImage(resource = imageResource, null,contentScale = ContentScale.Crop, modifier = Modifier.width(180.dp).height(280.dp).clip(RoundedCornerShape(10.dp)), crossfade = true)
        }
        is Resource.Failure -> {
            Text(text = imageResource.exception.message ?: "")
        }
    }
}


@Composable
fun fetchImage(url: String): ImageBitmap? {
    var image by remember(url) { mutableStateOf<ImageBitmap?>(null) }

    LaunchedEffect(url) {
        loadFullImage(url)?.let {
            image = org.jetbrains.skia.Image.makeFromEncoded(toByteArray(it)).toComposeImageBitmap()
        }
    }

    return image
}

fun loadFullImage(source: String): BufferedImage? {
    return try {
        val url = URL(source)
        val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
        connection.connectTimeout = 5000
        connection.connect()

        val input: InputStream = connection.inputStream
        val bitmap: BufferedImage? = ImageIO.read(input)
        bitmap
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun toByteArray(bitmap: BufferedImage): ByteArray {
    val baos = ByteArrayOutputStream()
    ImageIO.write(bitmap, "png", baos)
    return baos.toByteArray()
}