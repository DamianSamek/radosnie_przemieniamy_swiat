package com.wsiz.projekt_zespolowy.fragment.user.this_user

import android.os.Bundle
import android.view.View
import androidx.cardview.widget.CardView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.wsiz.projekt_zespolowy.R
import com.wsiz.projekt_zespolowy.activity.main.MainActivity
import com.wsiz.projekt_zespolowy.data.dto.Post
import com.wsiz.projekt_zespolowy.data.dto.UserPost
import com.wsiz.projekt_zespolowy.data.shared_preferences.SharedPreferences
import com.wsiz.projekt_zespolowy.databinding.ThisUserFragmentLayoutBinding
import com.wsiz.projekt_zespolowy.fragment.user.UserFragment
import com.wsiz.projekt_zespolowy.fragment.user.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ThisUserFragment : UserFragment<ThisUserFragmentLayoutBinding, ThisUserViewModel>() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    private val vm: ThisUserViewModel by viewModels()

    override fun getViewModel() = vm
    override fun getLayoutId() = R.layout.this_user_fragment_layout

    private fun onPostClick(cardView: CardView, userPost: UserPost) {
        val extras = FragmentNavigatorExtras(
            cardView to "postTransition"
        )

        val direction =
            ThisUserFragmentDirections.actionThisUserFragmentToEditPostFragment(Post.map(userPost))
        (activity as MainActivity).navigateTo(direction, extras)
    }

    override fun onViewModelStateChanged(state: UserViewModel.State) {
        when (state) {
            is ThisUserViewModel.OpenAddPost -> {
                vm.state.postValue(null)
                (activity as MainActivity).navigateTo(ThisUserFragmentDirections.actionThisUserFragmentToAddPostFragment())
            }
            is UserViewModel.State.PostClick -> onPostClick(state.cardView, state.userPost)
        }
    }
}