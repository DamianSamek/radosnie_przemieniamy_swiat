package com.wsiz.projekt_zespolowy.fragment.user

import com.wsiz.projekt_zespolowy.base.BaseViewModel
import com.wsiz.projekt_zespolowy.data.dto.UserPost
import com.wsiz.projekt_zespolowy.data.repository.PostRepository
import io.reactivex.Single

abstract class UserViewModel(
    private val postRepository: PostRepository
) :
    BaseViewModel() {

    fun loadPosts(userId: Int, pageNumber: Int): Single<List<UserPost>> {
        return postRepository.getByUser(userId, pageNumber)
    }

    fun like(postId: Int) {
        postRepository.like(postId)
    }
}