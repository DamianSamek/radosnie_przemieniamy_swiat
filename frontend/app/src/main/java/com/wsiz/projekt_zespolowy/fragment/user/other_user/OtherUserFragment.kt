package com.wsiz.projekt_zespolowy.fragment.user.other_user

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.wsiz.projekt_zespolowy.R
import com.wsiz.projekt_zespolowy.data.dto.UserPost
import com.wsiz.projekt_zespolowy.databinding.OtherUserFragmentLayoutBinding
import com.wsiz.projekt_zespolowy.fragment.user.UserFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OtherUserFragment : UserFragment<OtherUserFragmentLayoutBinding, OtherUserViewModel>() {

    private val navArguments: OtherUserFragmentArgs by navArgs()
    private val vm: OtherUserViewModel by viewModels()


    override fun getViewModel() = vm

    override fun getLayoutId() = R.layout.other_user_fragment_layout

    override fun getUserId() = navArguments.userId

    override fun onPostClick(userPost: UserPost) {
    }
}