package com.wsiz.projekt_zespolowy.fragment.edit_post

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.wsiz.projekt_zespolowy.R
import com.wsiz.projekt_zespolowy.activity.main.MainActivity
import com.wsiz.projekt_zespolowy.base.BaseFragment
import com.wsiz.projekt_zespolowy.databinding.EditPostFragmentLayoutBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditPostFragment : BaseFragment() {

    private lateinit var binding: EditPostFragmentLayoutBinding
    private val viewModel: EditPostViewModel by viewModels()

    private val navArguments: EditPostFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.edit_post_fragment_layout, container, false)
        binding.lifecycleOwner = this
        binding.setVariable(BR.viewModel, viewModel)
        binding.setVariable(BR.post, navArguments.post)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.postState.observe(viewLifecycleOwner, Observer { postState ->
            val context = context ?: return@Observer
            when (postState) {
                null, EditPostViewModel.PostState.INIT, EditPostViewModel.PostState.LOADING -> return@Observer
                EditPostViewModel.PostState.NO_DESCRIPTION -> Toast.makeText(
                    context,
                    R.string.add_post_fragment_no_description,
                    Toast.LENGTH_LONG
                ).show()
                EditPostViewModel.PostState.ERROR -> Toast.makeText(
                    context,
                    R.string.error,
                    Toast.LENGTH_LONG
                ).show()
                EditPostViewModel.PostState.SUCCESS -> {
                    Toast.makeText(context, R.string.edit_post_fragment_success, Toast.LENGTH_LONG)
                        .show()
                    (activity as MainActivity).navigateUp()
                }
                EditPostViewModel.PostState.REMOVED -> {
                    Toast.makeText(context, R.string.edit_post_fragment_removed, Toast.LENGTH_LONG)
                        .show()
                    (activity as MainActivity).navigateUp()
                }
            }
        })
    }
}