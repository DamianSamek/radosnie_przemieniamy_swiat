package com.wsiz.projekt_zespolowy.fragment.user.other_user

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.wsiz.projekt_zespolowy.R
import com.wsiz.projekt_zespolowy.databinding.OtherUserFragmentLayoutBinding
import com.wsiz.projekt_zespolowy.fragment.user.UserFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OtherUserFragment : UserFragment<OtherUserFragmentLayoutBinding, OtherUserViewModel>() {

    override val viewModel: OtherUserViewModel by viewModels()

    override fun getLayoutId() = R.layout.other_user_fragment_layout

    override fun getRecyclerView(): RecyclerView {
        return binding.recyclerView
    }
}