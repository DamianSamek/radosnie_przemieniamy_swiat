package com.wsiz.projekt_zespolowy.fragment.user.this_user

import androidx.fragment.app.viewModels
import com.wsiz.projekt_zespolowy.R
import com.wsiz.projekt_zespolowy.activity.MainActivity
import com.wsiz.projekt_zespolowy.data.dto.Post
import com.wsiz.projekt_zespolowy.data.dto.UserPost
import com.wsiz.projekt_zespolowy.data.shared_preferences.SharedPreferences
import com.wsiz.projekt_zespolowy.databinding.ThisUserFragmentLayoutBinding
import com.wsiz.projekt_zespolowy.fragment.user.UserFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ThisUserFragment : UserFragment<ThisUserFragmentLayoutBinding, ThisUserViewModel>() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    private val vm: ThisUserViewModel by viewModels()

    override fun getViewModel() = vm
    override fun getLayoutId() = R.layout.this_user_fragment_layout

    override fun onPostClick(userPost: UserPost) {
        val direction =
            ThisUserFragmentDirections.actionThisUserFragmentToEditPostFragment(Post.map(userPost))
        (activity as MainActivity).navigateTo(direction)
    }

    override fun getUserId() = sharedPreferences.getUserId()
}