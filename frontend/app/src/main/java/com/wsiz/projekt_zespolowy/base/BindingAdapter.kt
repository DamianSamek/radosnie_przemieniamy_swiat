package com.wsiz.projekt_zespolowy.base

import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.core.view.children
import androidx.databinding.BindingAdapter

object BindingAdapter {

    @BindingAdapter("bitmap")
    @JvmStatic
    fun setBitmap(view: ImageView, bitmap: Bitmap?) {
        if(bitmap == null) {
            view.visibility = View.GONE
        } else {
            view.setImageBitmap(bitmap)
            view.visibility = View.VISIBLE
        }
    }

    @BindingAdapter("bitmap")
    @JvmStatic
    fun setBitmap(view: CardView, bitmap: Bitmap?) {
        if(bitmap == null) {
            view.visibility = View.GONE
        } else {
            for(child in view.children) {
                if(child is ImageView) {
                    child.setImageBitmap(bitmap)
                }
            }
            view.visibility = View.VISIBLE
        }
    }
}