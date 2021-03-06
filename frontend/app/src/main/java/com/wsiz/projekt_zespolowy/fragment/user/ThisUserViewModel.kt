package com.wsiz.projekt_zespolowy.fragment.user

import android.content.Intent
import android.view.View
import androidx.hilt.lifecycle.ViewModelInject
import com.wsiz.projekt_zespolowy.activity.login.LoginActivity
import com.wsiz.projekt_zespolowy.base.recycler_view_adapter.BasePostsAdapter
import com.wsiz.projekt_zespolowy.data.repository.PostRepository
import com.wsiz.projekt_zespolowy.data.shared_preferences.SharedPreferences

class ThisUserViewModel @ViewModelInject constructor(
    private val sharedPreferences: SharedPreferences,
    postRepository: PostRepository
) : UserViewModel(sharedPreferences.getUserId(), postRepository) {

    class OpenAddPost : State()

    fun logout(view: View) {
        val context = view.context ?: return

        sharedPreferences.apply {
            putToken("")
            putUserId(-1)
            putUserName("")
        }

        val intent = Intent(context, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or
                Intent.FLAG_ACTIVITY_CLEAR_TASK or
                Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

    fun openAddPostFragment() {
        state.postValue(OpenAddPost())
    }

    override fun buildRecycleViewAdapter() = BasePostsAdapter(this)

    fun getPostsAdapter() = getRecyclerViewAdapter() as BasePostsAdapter

    fun getUsername() = sharedPreferences.getUserName()
}