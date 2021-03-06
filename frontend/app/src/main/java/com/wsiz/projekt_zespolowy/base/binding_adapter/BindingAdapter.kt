package com.wsiz.projekt_zespolowy.base.binding_adapter

import android.graphics.Bitmap
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.core.view.children
import androidx.core.widget.doOnTextChanged
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import com.squareup.picasso.Picasso

object BindingAdapter {

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

    @BindingAdapter("statefulAdapter")
    @JvmStatic
    fun setAdapter(recyclerView: RecyclerView, adapter: RecyclerView.Adapter<*>) {
        recyclerView.adapter = adapter

        /** Doesn't work as expected in 1.2.0-beta01 version **/
//        if(adapter is ConcatAdapter) {
//            for(subAdapter in adapter.adapters) {
//                subAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.ALLOW
//            }
//        }
//        else {
//            adapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.ALLOW
//        }
    }

    @BindingAdapter("cancelErrorEditText", "isErrorEnabled", requireAll = true)
    @JvmStatic
    fun setError(
        textInputLayout: TextInputLayout,
        editText: EditText,
        isErrorEnabled: Boolean
    ) {
        if (isErrorEnabled) {
            textInputLayout.isErrorEnabled = true
            textInputLayout.error = " "

            editText.doOnTextChanged { text, _, _, _ ->
                textInputLayout.isErrorEnabled =  textInputLayout.isErrorEnabled && text.isNullOrEmpty()
            }
        } else {
            textInputLayout.isErrorEnabled = false
        }
    }
}