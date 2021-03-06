package com.wsiz.projekt_zespolowy.fragment.user

import androidx.cardview.widget.CardView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.wsiz.projekt_zespolowy.R
import com.wsiz.projekt_zespolowy.data.dto.Post
import com.wsiz.projekt_zespolowy.data.dto.UserPost
import com.wsiz.projekt_zespolowy.data.shared_preferences.SharedPreferences
import com.wsiz.projekt_zespolowy.databinding.ThisUserFragmentLayoutBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ThisUserFragment : UserFragment<ThisUserFragmentLayoutBinding, ThisUserViewModel>() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override val viewModel: ThisUserViewModel by viewModels()

    override fun getLayoutId() = R.layout.this_user_fragment_layout

    private fun onPostClick(cardView: CardView, userPost: UserPost) {
        val extras = FragmentNavigatorExtras(
            cardView to "postTransition"
        )

        val direction =
            ThisUserFragmentDirections.actionThisUserFragmentToEditPostFragment(
                Post.map(userPost)
            )
        mainActivity().navigateTo(direction, extras)
    }

    override fun onViewModelStateChanged(state: UserViewModel.State) {
        when (state) {
            is ThisUserViewModel.OpenAddPost -> {
                mainActivity().navigateTo(ThisUserFragmentDirections.actionThisUserFragmentToAddPostFragment())
            }
            is UserViewModel.State.PostClick -> onPostClick(state.cardView, state.userPost)
        }
    }

    override fun getRecyclerView(): RecyclerView {
        return binding.recyclerView
    }
}