package com.wsiz.projekt_zespolowy.fragment.add_post

import android.graphics.Bitmap
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.wsiz.projekt_zespolowy.base.fragment.view_model.BaseViewModel
import com.wsiz.projekt_zespolowy.data.dto.Post
import com.wsiz.projekt_zespolowy.data.network.FirebaseStorageService
import com.wsiz.projekt_zespolowy.data.repository.PostRepository

class AddPostViewModel @ViewModelInject constructor(
    private val postRepository: PostRepository,
    private val firebaseStorageService: FirebaseStorageService
) : BaseViewModel<AddPostViewModel.State>() {

    enum class State {
        NO_IMAGE_AND_DESCRIPTION, NO_IMAGE, NO_DESCRIPTION, LOADING, ERROR, SUCCESS
    }

    override val state = MutableLiveData<State>()
    val postImage = MutableLiveData<Bitmap>()

    fun setPostImage(newPostImage: Bitmap?) {
        postImage.postValue(newPostImage)
    }

    fun removeImage() {
        postImage.postValue(null)
    }

    fun addPost(description: String) {
        val bitmap = postImage.value

        when {
            (bitmap == null) and description.isEmpty() -> {
                state.postValue(State.NO_IMAGE_AND_DESCRIPTION)
            }
            bitmap == null -> {
                state.postValue(State.NO_IMAGE)
            }
            description.isEmpty() -> {
                state.postValue(State.NO_DESCRIPTION)
            }
            else -> {
                state.postValue(State.LOADING)

                firebaseStorageService.uploadPostImage(
                    bitmap,
                    { url, uuid -> addPost(Post(description, url, uuid)) },
                    { state.postValue(State.ERROR) })
            }
        }
    }

    private fun addPost(post: Post) {
        postRepository.create(post).io({
            state.postValue(State.SUCCESS)
        }, {
            state.postValue(State.ERROR)
        })
    }
}