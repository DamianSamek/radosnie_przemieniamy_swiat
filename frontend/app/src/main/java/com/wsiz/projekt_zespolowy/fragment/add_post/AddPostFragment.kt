package com.wsiz.projekt_zespolowy.fragment.add_post

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.wsiz.projekt_zespolowy.R
import com.wsiz.projekt_zespolowy.activity.MainActivity
import com.wsiz.projekt_zespolowy.databinding.AddPostFragmentLayoutBinding
import com.wsiz.projekt_zespolowy.utils.ImageUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.add_post_fragment_layout.*

@AndroidEntryPoint
class AddPostFragment : Fragment() {

    companion object {
        const val READ_STORAGE_PERMISSION_CODE = 1
        const val CHOOSE_IMAGE = 1
    }

    private lateinit var binding: AddPostFragmentLayoutBinding
    private val viewModel: AddPostViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.add_post_fragment_layout, container, false)
        binding.lifecycleOwner = this
        binding.setVariable(BR.viewModel, viewModel)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addImageCardView.setOnClickListener {
            checkGalleryPermission()
        }

        viewModel.postState.observe(viewLifecycleOwner, Observer { postState ->
            val context = context ?: return@Observer
            when (postState) {
                null, AddPostViewModel.PostState.INIT, AddPostViewModel.PostState.LOADING -> return@Observer
                AddPostViewModel.PostState.NO_IMAGE -> Toast.makeText(
                    context,
                    R.string.add_post_fragment_no_image,
                    Toast.LENGTH_LONG
                ).show()
                AddPostViewModel.PostState.NO_DESCRIPTION -> Toast.makeText(
                    context,
                    R.string.add_post_fragment_no_description,
                    Toast.LENGTH_LONG
                ).show()
                AddPostViewModel.PostState.ERROR -> Toast.makeText(
                    context,
                    R.string.error,
                    Toast.LENGTH_LONG
                ).show()
                AddPostViewModel.PostState.SUCCESS -> {
                    Toast.makeText(context, R.string.add_post_fragment_success, Toast.LENGTH_LONG)
                        .show()
                    (activity as MainActivity).navigateTo(R.id.action_addPostFragment_to_thisUserFragment)

                    forgetData()
                }
            }
        })
    }

    private fun forgetData() {
        descriptionView.setText("")
        viewModel.forgetData()
    }

    private fun openGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(
                intent,
                getString(R.string.add_post_fragment_choose_image)
            ), CHOOSE_IMAGE
        )
    }

    private fun checkGalleryPermission() {
        if (isGalleryPermissionGranted()) {
            openGallery()
        } else {
            val activity = activity
            if (activity != null) {
                ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    READ_STORAGE_PERMISSION_CODE
                )
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, result: Intent?) {
        if (requestCode == CHOOSE_IMAGE) {
            val activity = activity ?: return
            val image = ImageUtils.getChosenImage(activity, result)
            viewModel.setPostImage(image)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == READ_STORAGE_PERMISSION_CODE) {
            if (isGalleryPermissionGranted()) {
                openGallery()
            }
        }
    }

    private fun isGalleryPermissionGranted(): Boolean {
        val activity = activity
        return if (activity != null) {
            ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            false
        }
    }
}