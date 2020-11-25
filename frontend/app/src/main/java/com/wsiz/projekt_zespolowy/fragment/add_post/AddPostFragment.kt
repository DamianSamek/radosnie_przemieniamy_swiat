package com.wsiz.projekt_zespolowy.fragment.add_post

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.wsiz.projekt_zespolowy.R
import com.wsiz.projekt_zespolowy.databinding.AddPostFragmentLayoutBinding
import com.wsiz.projekt_zespolowy.utils.ImageUtils
import java.io.InputStream


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

        binding.addImageView.setOnClickListener {
            checkGalleryPermission()
        }
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