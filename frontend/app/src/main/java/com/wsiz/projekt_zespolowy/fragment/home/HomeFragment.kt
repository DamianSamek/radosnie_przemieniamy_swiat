package com.wsiz.projekt_zespolowy.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.wsiz.projekt_zespolowy.R
import com.wsiz.projekt_zespolowy.base.fragment.BaseStatefulRecyclerViewFragment
import com.wsiz.projekt_zespolowy.data.dto.UserPost
import com.wsiz.projekt_zespolowy.data.shared_preferences.SharedPreferences
import com.wsiz.projekt_zespolowy.databinding.HomeFragmentLayoutBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseStatefulRecyclerViewFragment<HomeViewModel>() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    private lateinit var binding: HomeFragmentLayoutBinding
    override val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.home_fragment_layout, container, false)
        binding.lifecycleOwner = this
        binding.setVariable(BR.viewModel, viewModel)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.state.observe(viewLifecycleOwner, Observer {
            when (it) {
                null -> return@Observer
                is HomeViewModel.State.OnPostClick -> {
                    onPostClick(it.cardView, it.userPost)

                }
                is HomeViewModel.State.ErrorLoading -> {
                    onErrorLoading(it.error)
                }
            }
        })
    }

    private fun onErrorLoading(error: Throwable) {
        val context = context ?: return
        Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT).show()
    }

    private fun onPostClick(cardView: CardView, userPost: UserPost) {
        if (sharedPreferences.getUserId() == userPost.userId) {
            mainActivity().navigateTo(
                HomeFragmentDirections.actionHomeFragmentToThisUserFragment()
            )
        } else {
            mainActivity().navigateTo(
                HomeFragmentDirections.actionHomeFragmentToOtherUserFragment(userPost.userId)
            )
        }
    }

    override fun getRecyclerView(): RecyclerView {
        return binding.recyclerView
    }
}