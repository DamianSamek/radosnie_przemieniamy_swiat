package com.wsiz.projekt_zespolowy.fragment.user

import androidx.hilt.lifecycle.ViewModelInject
import com.wsiz.projekt_zespolowy.base.BaseViewModel
import com.wsiz.projekt_zespolowy.data.repository.PostRepository
import com.wsiz.projekt_zespolowy.data.dto.Post
import com.wsiz.projekt_zespolowy.data.dto.UserPost
import com.wsiz.projekt_zespolowy.data.shared_preferences.SharedPreferences
import io.reactivex.Single

class UserViewModel @ViewModelInject constructor(private val postRepository: PostRepository) :
    BaseViewModel() {

    fun loadPosts(userId: Int, pageNumber: Int): Single<List<UserPost>> {
        return postRepository.getByUser(userId, pageNumber)
    }
}