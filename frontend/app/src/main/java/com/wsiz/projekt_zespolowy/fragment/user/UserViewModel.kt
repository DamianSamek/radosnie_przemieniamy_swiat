package com.wsiz.projekt_zespolowy.fragment.user

import android.content.Intent
import android.view.View
import androidx.hilt.lifecycle.ViewModelInject
import com.wsiz.projekt_zespolowy.activity.login.LoginActivity
import com.wsiz.projekt_zespolowy.base.BaseViewModel
import com.wsiz.projekt_zespolowy.data.dto.UserPost
import com.wsiz.projekt_zespolowy.data.repository.PostRepository
import com.wsiz.projekt_zespolowy.data.shared_preferences.SharedPreferences
import io.reactivex.Single

class UserViewModel @ViewModelInject constructor(
    private val sharedPreferences: SharedPreferences,
    private val postRepository: PostRepository
) :
    BaseViewModel() {

    fun loadPosts(userId: Int, pageNumber: Int): Single<List<UserPost>> {
        return postRepository.getByUser(userId, pageNumber)
    }

    fun like(postId: Int) {
        postRepository.like(postId)
    }

    fun logout(view: View) {
        val context = view.context ?: return

        sharedPreferences.apply {
            putToken("")
            putUserId(-1)
        }

        val intent = Intent(context, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or
                Intent.FLAG_ACTIVITY_CLEAR_TASK or
                Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }
}