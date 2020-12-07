package com.wsiz.projekt_zespolowy.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Environment
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Exception

object ImageUtils {
    fun getChosenImage(activity: Activity, result: Intent?): Bitmap? {
        val imageData = result?.data
        return if (imageData != null) {
            val input = activity.contentResolver?.openInputStream(imageData)
            val bitmap = BitmapFactory.decodeStream(input)

            // 200 kB
            scaleToFileSize(activity, bitmap, 1 * 1000 * 200)
        } else {
            null
        }
    }

    private fun scaleToFileSize(context: Context, bitmap: Bitmap, maxFileSize: Long): Bitmap {
        var scaledBitmap = bitmap
        val file = scaledBitmap.toFile(context)
        val size = file.length()

        if (file.length() > maxFileSize) {
            val scalePercentage = maxFileSize / size.toFloat()
            scaledBitmap =
                Bitmap.createScaledBitmap(
                    bitmap,
                    (bitmap.width * scalePercentage).toInt(),
                    (bitmap.height * scalePercentage).toInt(),
                    false
                )
        }
        return scaledBitmap
    }

    private fun Bitmap.toFile(context: Context): File {
        val bytes = ByteArrayOutputStream()
        compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val file = File(
            context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                .toString() + File.separator + "temp_radosnie_przemieniamy_swiat.jpg"
        )
        try {
            val fo = FileOutputStream(file)
            fo.write(bytes.toByteArray())
            fo.flush()
            fo.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return file
    }

    fun toByteArray(bitmap: Bitmap, quality: Int = 100): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream)
        return stream.toByteArray()
    }


    fun generatePicassoTarget(onError: (Exception?) -> Unit = {}, onLoaded: (Bitmap?) -> Unit): Target {
        return object : Target {
            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                onError(e)
            }

            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                onLoaded(bitmap)
            }
        }
    }
}