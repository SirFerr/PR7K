@file:Suppress("DEPRECATION")
@file:OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class
)

package com.example.pr7k.ui.view

import android.media.MediaScannerConnection
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.pr7k.ui.theme.PR7KTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.net.URL

class MainActivity : ComponentActivity() {
    object TextStyles {
        val testTextStyle = TextStyle(
            fontSize = 24.sp,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Light,
            color = Color.White
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PR7KTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background

                ) {
                    urlToImage()


                }
            }
        }
    }
}

@Composable
fun urlToImage() {


    var fileDownloaded by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current


    var imageURL by remember {
        mutableStateOf("https://cdn.shopclues.com/images/thumbnails/79835/320/320/104787525124666394ID1006929615021796911502242942.jpg")
    }
    var text by remember { mutableStateOf("https://www.simplilearn.com/ice9/free_resources_article_thumb/what_is_image_Processing.jpg") }
    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        LazyRow {
            item {
                Image(
                    painter = rememberAsyncImagePainter(imageURL),
                    contentDescription = null,
                    modifier = Modifier.size(300.dp)
                )
            }
            item {
                Image(
                    painter = rememberAsyncImagePainter(imageURL),
                    contentDescription = null,
                    modifier = Modifier.size(300.dp)
                )
            }
            item {
                Image(
                    painter = rememberAsyncImagePainter(imageURL),
                    contentDescription = null,
                    modifier = Modifier.size(300.dp)
                )
            }
        }

        TextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Url") },
            singleLine = true
        )



        Button(
            onClick = {
                imageURL = text
            }) {
            Text("Search")
        }

        Button(
            onClick = {
                Toast.makeText(
                    context,
                    "Please wait, it may take a few minutes...",
                    Toast.LENGTH_SHORT
                ).show()
                GlobalScope.launch(Dispatchers.IO) {
                    val url = URL(imageURL).openStream()
                    val outputDir =
                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                    val outputFile = File(outputDir, "1.jpg")
                    val outputStream = FileOutputStream(outputFile)
                    val buffer = ByteArray(4 * 1024)
                    var bytesRead: Int
                    while (url.read(buffer).also { bytesRead = it } != -1) {
                        outputStream.write(buffer, 0, bytesRead)
                    }
                    url.close()
                    outputStream.close()
                    withContext(Dispatchers.Main) {
                        MediaScannerConnection.scanFile(
                            context,
                            arrayOf(outputFile.toString()),
                            null,
                            null
                        )
                    }
                }

            }) {
            Text("Save")
        }
    }
}