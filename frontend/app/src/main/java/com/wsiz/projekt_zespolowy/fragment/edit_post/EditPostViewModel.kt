package com.wsiz.projekt_zespolowy.fragment.edit_post

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.wsiz.projekt_zespolowy.base.BaseViewModel
import com.wsiz.projekt_zespolowy.data.dto.EditPost
import com.wsiz.projekt_zespolowy.data.dto.Post
import com.wsiz.projekt_zespolowy.data.network.FirebaseStorageService
import com.wsiz.projekt_zespolowy.data.repository.PostRepository

class EditPostViewModel @ViewModelInject constructor(
    private val postRepository: PostRepository,
    private val firebaseStorageService: FirebaseStorageService
) :
    BaseViewModel() {

    enum class PostState {
        INIT, NO_DESCRIPTION, LOADING, ERROR, SUCCESS, REMOVED
    }

    val postState = MutableLiveData<PostState>()

    fun editPost(post: Post, description: String) {
        when {
            description.isEmpty() -> {
                postState.postValue(PostState.NO_DESCRIPTION)
            }
            else -> {
                postState.postValue(PostState.LOADING)
                editPost(post.apply { this.description = description })
            }
        }
    }

    fun removePost(post: Post) {
        postState.postValue(PostState.LOADING)

        firebaseStorageService.removePostImage(post.uuid) {
            postRepository.delete(post.id).io(
                { postState.postValue(PostState.REMOVED) },
                { postState.postValue(PostState.ERROR) }
            )
        }
    }

    private fun editPost(post: Post) {
        postRepository.update(EditPost.map(post)).io({
            postState.postValue(PostState.SUCCESS)
        }, {
            postState.postValue(PostState.ERROR)
        })
    }
}