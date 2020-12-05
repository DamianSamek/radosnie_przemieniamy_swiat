package com.wsiz.projekt_zespolowy.fragment.home

import androidx.hilt.lifecycle.ViewModelInject
import com.wsiz.projekt_zespolowy.base.BaseViewModel
import com.wsiz.projekt_zespolowy.data.dto.Post
import com.wsiz.projekt_zespolowy.data.repository.PostRepository
import io.reactivex.Single

class HomeViewModel @ViewModelInject constructor(private val postRepository: PostRepository) :
    BaseViewModel() {

    fun loadPosts(pageNumber: Int): Single<List<Post>> {
        return postRepository.getAll(pageNumber)
    }
}