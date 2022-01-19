package com.jackz.kmmovies.android

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jackz.kmmovies.Greeting
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.jackz.kmmovies.Movie
import com.jackz.kmmovies.android.ui.theme.Purple500
import com.jackz.kmmovies.android.ui.theme.Purple700
import com.jackz.kmmovies.android.ui.theme.SampleComposeTheme
import com.jackz.kmmovies.repository.MovieRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main
import java.util.*
import kotlin.coroutines.CoroutineContext

fun greet(): String {
    return Greeting().greeting()
}

@ExperimentalFoundationApi
class MainActivity() : ComponentActivity(),CoroutineScope {

    private lateinit var job: Job

    override val coroutineContext: CoroutineContext
        get() = job + Main

    val scope = CoroutineScope(Job() + Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SampleComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting(name = Greeting().greeting())
                }
            }
        }
    }
}


@ExperimentalFoundationApi
@Composable
fun Greeting(name: String) {
    var movieList by remember { mutableStateOf(emptyList<Movie>()) }
    var imageBitmap  by remember { mutableStateOf<Bitmap?>(null)}

    LaunchedEffect(true) {
        val repo = MovieRepository()
        repo.getMovies {
            movieList = it.results
        }

        repo.getImage("https://image.tmdb.org/t/p/w185", {
            imageBitmap = it
        }, {

        })

    }
    Column {
        TopAppBar(
            title = {
                Text(text = "Movies")
            },
            elevation = 2.dp
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyVerticalGrid(cells = GridCells.Fixed(3), modifier = Modifier.fillMaxSize()) {
            items(movieList) { movies ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = rememberImagePainter("https://image.tmdb.org/t/p/w185" + movies.posterPath),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(120.dp)
                            .height(180.dp)
                            .clip(RoundedCornerShape(10.dp))
                    )
                    Text(text = movies.title, maxLines = 1, fontSize = 14.sp,modifier = Modifier.padding(8.dp))
                    Spacer(modifier = Modifier.height(4.dp))

                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}


//Image(
//painter = rememberImagePainter("https://image.tmdb.org/t/p/w185/1g0dhYtq4irTY1GPXvft6k4YLjm.jpg"),
//contentDescription = null,
//contentScale = ContentScale.Crop,
//modifier = Modifier
//.width(120.dp)
//.height(180.dp)
//.clip(RoundedCornerShape(10.dp))
//)