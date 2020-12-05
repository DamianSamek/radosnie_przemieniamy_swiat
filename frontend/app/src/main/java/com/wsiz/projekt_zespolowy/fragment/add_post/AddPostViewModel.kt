package com.wsiz.projekt_zespolowy.fragment.add_post

import android.graphics.Bitmap
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.wsiz.projekt_zespolowy.base.BaseViewModel
import com.wsiz.projekt_zespolowy.data.network.FirebaseStorageService
import com.wsiz.projekt_zespolowy.data.repository.PostRepository
import com.wsiz.projekt_zespolowy.data.dto.Post
import com.wsiz.projekt_zespolowy.data.shared_preferences.SharedPreferences

class AddPostViewModel @ViewModelInject constructor(
    private val sharedPreferences: SharedPreferences,
    private val postRepository: PostRepository,
    private val firebaseStorageService: FirebaseStorageService
) :
    BaseViewModel() {

    enum class PostState {
        INIT, NO_IMAGE, NO_DESCRIPTION, LOADING, ERROR, SUCCESS
    }

    val postState = MutableLiveData<PostState>()
    val postImage = MutableLiveData<Bitmap>()

    fun setPostImage(newPostImage: Bitmap?) {
        postImage.postValue(newPostImage)
    }

    fun addPost(description: String) {
        val bitmap = postImage.value

        when {
            bitmap == null -> {
                postState.postValue(PostState.NO_IMAGE)
            }
            description.isEmpty() -> {
                postState.postValue(PostState.NO_DESCRIPTION)
            }
            else -> {
                postState.postValue(PostState.LOADING)

                val userId = sharedPreferences.getUserId()
                firebaseStorageService.uploadPostImage(
                    userId,
                    bitmap,
                    { url, uuid -> addPost(Post(description, url)) },
                    { postState.postValue(PostState.ERROR) })
            }
        }
    }

    private fun addPost(post: Post) {
        postRepository.create(post).io({
            postState.postValue(PostState.SUCCESS)
        }, {
            postState.postValue(PostState.ERROR)
        })
    }

    fun forgetData() {
        postState.postValue(PostState.INIT)
        postImage.postValue(null)
    }
}