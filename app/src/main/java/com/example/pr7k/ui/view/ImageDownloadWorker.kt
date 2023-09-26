package com.example.pr7k.ui.view

import android.content.Context
import android.media.MediaScannerConnection
import android.os.Environment
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Locale

class ImageDownloadWorker(context: Context, params: WorkerParameters) :
    Worker(context, params) {
    override fun doWork(): Result {
        val imageURL = inputData.getString("image_url")

        if (imageURL.isNullOrEmpty()) {
            return Result.failure()
        }

        val name = SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS", Locale.US)
            .format(System.currentTimeMillis())
        val outputDir =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)

        try {
            val url = URL(imageURL).openStream()
            val outputFile = File(outputDir, "${name}.jpg")
            val outputStream = FileOutputStream(outputFile)
            val buffer = ByteArray(4 * 1024)
            var bytesRead: Int
            while (url.read(buffer).also { bytesRead = it } != -1) {
                outputStream.write(buffer, 0, bytesRead)
            }
            url.close()
            outputStream.close()

            MediaScannerConnection.scanFile(
                applicationContext,
                arrayOf(outputFile.toString()),
                null,
                null
            )

            return Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.failure()
        }
    }
}