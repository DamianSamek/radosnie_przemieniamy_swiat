package com.wsiz.projekt_zespolowy.fragment.add_post

import android.graphics.Bitmap
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.wsiz.projekt_zespolowy.base.BaseViewModel
import com.wsiz.projekt_zespolowy.data.repository.PostRepository
import com.wsiz.projekt_zespolowy.data.services.Post

class AddPostViewModel @ViewModelInject constructor(private val postRepository: PostRepository) :
    BaseViewModel() {

    enum class PostState {
        INIT, NO_IMAGE, NO_DESCRIPTION, ERROR, SUCCESS
    }

    val postState = MutableLiveData<PostState>().apply { postValue(PostState.INIT) }
    val postImage = MutableLiveData<Bitmap>()

    fun setPostImage(newPostImage: Bitmap?) {
        postImage.postValue(newPostImage)
    }

    fun addPost(description: String) {
        when {
            postImage.value == null -> {
                postState.postValue(PostState.NO_IMAGE)
            }
            description.isEmpty() -> {
                postState.postValue(PostState.NO_DESCRIPTION)
            }
            else -> {
                // add image to firebase
                val post = Post(description, "")
                postRepository.create(post).io({
                    postState.postValue(PostState.SUCCESS)
                }, {
                    postState.postValue(PostState.ERROR)
                })
            }
        }
    }
}