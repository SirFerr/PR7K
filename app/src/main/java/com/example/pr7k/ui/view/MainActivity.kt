package com.example.pr7k.ui.view

import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.pr7k.ui.theme.PR7KTheme
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun urlToImage(modifier: Modifier = Modifier) {
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
//                CoroutineScope(Dispatchers.IO).launch {
//                    saveImage(
//                        Glide.with()
//                            .asBitmap()
//                            .load("https://i.imgur.com/4HFRb2z.jpg") // sample image
//                            .placeholder(android.R.drawable.progress_indeterminate_horizontal) // need placeholder to avoid issue like glide annotations
//                            .error(android.R.drawable.stat_notify_error) // need error to avoid issue like glide annotations
//                            .submit()
//                            .get()
//                    )
//                }
            }) {
            Text("Save")
        }
    }


}

fun saveImage(image: Bitmap): String? {
    var savedImagePath: String? = null
    val imageFileName = "JPEG_" + "FILE_NAME" + ".jpg"
    val storageDir = File(
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            .toString() + "/YOUR_FOLDER_NAME"
    )
    var success = true
    if (!storageDir.exists()) {
        success = storageDir.mkdirs()
    }
    if (success) {
        val imageFile = File(storageDir, imageFileName)
        savedImagePath = imageFile.absolutePath
        try {
            val fOut: OutputStream = FileOutputStream(imageFile)
            image.compress(Bitmap.CompressFormat.JPEG, 100, fOut)
            fOut.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // Add the image to the system gallery
        //Toast.makeText(this, "IMAGE SAVED", Toast.LENGTH_LONG).show() // to make this working, need to manage coroutine, as this execution is something off the main thread
    }
    return savedImagePath
}


