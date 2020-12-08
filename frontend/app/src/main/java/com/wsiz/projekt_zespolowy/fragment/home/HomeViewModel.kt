package com.wsiz.projekt_zespolowy.fragment.home

import androidx.hilt.lifecycle.ViewModelInject
import com.wsiz.projekt_zespolowy.base.view_model.BaseViewModel
import com.wsiz.projekt_zespolowy.data.dto.UserPost
import com.wsiz.projekt_zespolowy.data.repository.PostRepository
import io.reactivex.Single

class HomeViewModel @ViewModelInject constructor(private val postRepository: PostRepository) :
    BaseViewModel() {

    fun loadPosts(pageNumber: Int): Single<List<UserPost>> {
        return postRepository.getAll(pageNumber)
    }

    fun like(postId: Int) {
        postRepository.like(postId)
    }
}