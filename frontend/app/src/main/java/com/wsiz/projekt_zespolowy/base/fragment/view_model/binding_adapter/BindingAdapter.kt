package com.wsiz.projekt_zespolowy.base.fragment.view_model.binding_adapter

import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.core.view.children
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.EdgeTreatment
import com.google.android.material.shape.MaterialShapeDrawable
import com.squareup.picasso.Picasso
import com.wsiz.projekt_zespolowy.R
import com.wsiz.projekt_zespolowy.base.recycler_view_adapter.BaseAdapter

object BindingAdapter {

    @BindingAdapter("bitmap")
    @JvmStatic
    fun setBitmap(view: ImageView, bitmap: Bitmap?) {
        if (bitmap == null) {
            view.visibility = View.GONE
        } else {
            view.setImageBitmap(bitmap)
            view.visibility = View.VISIBLE
        }
    }

    @BindingAdapter("bitmap")
    @JvmStatic
    fun setBitmap(view: CardView, bitmap: Bitmap?) {
        if (bitmap == null) {
            view.visibility = View.GONE
        } else {
            for (child in view.children) {
                if (child is ImageView) {
                    child.setImageBitmap(bitmap)
                }
            }
            view.visibility = View.VISIBLE
        }
    }

    @BindingAdapter("imageURL")
    @JvmStatic
    fun setImageByUrl(view: ImageView, url: String) {
        Picasso.get().load(url).into(view)
    }

    @BindingAdapter("adapter")
    @JvmStatic
    fun setAdapter(recyclerView: RecyclerView, adapter: BaseAdapter<*>) {
        adapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.ALLOW
        recyclerView.adapter = adapter
    }
}