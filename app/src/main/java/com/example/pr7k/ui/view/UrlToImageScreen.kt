@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.pr7k.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import coil.compose.rememberAsyncImagePainter

@Composable
fun urlToImage(modifier: Modifier) {

    val context = LocalContext.current


    var imageURL by remember {
        mutableStateOf("https://cdn.shopclues.com/images/thumbnails/79835/320/320/104787525124666394ID1006929615021796911502242942.jpg")
    }
    var text by remember { mutableStateOf("https://www.simplilearn.com/ice9/free_resources_article_thumb/what_is_image_Processing.jpg") }

    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(16.dp)

            .fillMaxSize(),
    ) {


        LazyRow {

            for (i in 0..4)
                item {
                    Image(
                        painter = rememberAsyncImagePainter(imageURL),
                        contentDescription = null,
                        modifier = Modifier.size(300.dp),
                        contentScale = ContentScale.Crop

                    )
                }

        }

        TextField(
            value = text,
            shape = RoundedCornerShape(percent = 10),
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
                val workManager = WorkManager.getInstance(context)

                val constraints = Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()

                val inputData = Data.Builder()
                    .putString("image_url", imageURL)
                    .build()

                val downloadImageWorkRequest =
                    OneTimeWorkRequestBuilder<ImageDownloadWorker>()
                        .setInputData(inputData)
                        .setConstraints(constraints)
                        .build()

                workManager.enqueue(downloadImageWorkRequest)

            }) {
            Text("Save")
        }
    }
}