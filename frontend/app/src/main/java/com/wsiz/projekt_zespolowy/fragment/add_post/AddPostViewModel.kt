package com.wsiz.projekt_zespolowy.fragment.add_post

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddPostViewModel : ViewModel() {

    val postImage = MutableLiveData<Bitmap>()

    fun setPostImage(newPostImage: Bitmap?) {
        postImage.postValue(newPostImage)
    }
}