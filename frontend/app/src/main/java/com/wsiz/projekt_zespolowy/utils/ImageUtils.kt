package com.wsiz.projekt_zespolowy.utils

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory

class ImageUtils {

    companion object {
        fun getChosenImage(activity: Activity?, result: Intent?): Bitmap? {
            val imageData = result?.data
            return if (imageData != null) {
                val input = activity?.contentResolver?.openInputStream(imageData)
                BitmapFactory.decodeStream(input)
            } else {
                null
            }
        }
    }
}