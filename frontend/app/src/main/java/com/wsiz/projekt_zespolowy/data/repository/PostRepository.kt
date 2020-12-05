package com.wsiz.projekt_zespolowy.data.repository

import com.wsiz.projekt_zespolowy.data.dto.Post
import com.wsiz.projekt_zespolowy.data.dto.UserPost
import com.wsiz.projekt_zespolowy.data.network.service.PostService
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PostRepository @Inject constructor(private val postService: PostService) {

    fun create(post: Post): Completable {
        return postService.create(post)
    }

    fun delete(postId: Int): Completable {
        return postService.delete(postId)
    }

    fun update(post: Post): Completable {
        return postService.update(post)
    }

    fun getAll(pageNumber: Int): Single<List<UserPost>> {
        return postService.getAll(pageNumber).flatMap { userPostResponseList ->
            Observable.fromIterable(userPostResponseList).map { userPostResponse ->
                UserPost.map(userPostResponse)
            }.toList()
        }
    }

    fun getByUser(userId: Int, pageNumber: Int): Single<List<UserPost>> {
        return postService.getByUser(userId, pageNumber).flatMap { userPostResponseList ->
            Observable.fromIterable(userPostResponseList).map { userPostResponse ->
                UserPost.map(userPostResponse)
            }.toList()
        }
    }

    fun like(postId: Int) {
        postService.like(postId).subscribeOn(Schedulers.io()).onErrorComplete().subscribe()
    }
}