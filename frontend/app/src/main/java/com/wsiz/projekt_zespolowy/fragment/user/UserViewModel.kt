package com.wsiz.projekt_zespolowy.fragment.user

import androidx.hilt.lifecycle.ViewModelInject
import com.wsiz.projekt_zespolowy.base.BaseViewModel
import com.wsiz.projekt_zespolowy.data.repository.PostRepository
import com.wsiz.projekt_zespolowy.data.services.Post
import io.reactivex.Single

class UserViewModel @ViewModelInject constructor(private val postRepository: PostRepository) :
    BaseViewModel() {

    fun loadPosts(pageNumber: Int): Single<List<Post>> {
        return postRepository.getAll(pageNumber)
    }
}