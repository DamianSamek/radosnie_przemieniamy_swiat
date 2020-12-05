package com.wsiz.projekt_zespolowy.data.repository

import com.wsiz.projekt_zespolowy.data.network.service.PostService
import com.wsiz.projekt_zespolowy.data.dto.Post
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class PostRepository @Inject constructor(private val postService: PostService) {

    fun create(post: Post): Completable {
        return postService.create(post)
    }

    fun getAll(pageNumber: Int): Single<List<Post>> {
        return postService.getAll(pageNumber)
    }

    fun getByUser(userId: Int, pageNumber: Int): Single<List<Post>> {
        return postService.getByUser(userId, pageNumber)
    }
}