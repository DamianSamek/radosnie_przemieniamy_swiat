package com.wsiz.projekt_zespolowy.utils

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.core.os.bundleOf
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgs
import com.wsiz.projekt_zespolowy.data.dto.Post
import java.io.Serializable

object SavedStateHandleHelper {

    inline fun <reified T : NavArgs> safeArgs(savedStateHandle: SavedStateHandle): T {
        return T::class.java.getMethod("fromBundle", Bundle::class.java)
            .invoke(null, toBundle(savedStateHandle)) as T
    }


    fun toBundle(savedStateHandle: SavedStateHandle): Bundle {
        val bundle = bundleOf()
        for (key in savedStateHandle.keys()) {
            when {
                savedStateHandle.get<Int>(key) is Int -> {
                    bundle.putInt(key, savedStateHandle.get<Int>(key)!!)
                }
                savedStateHandle.get<String>(key) is String -> {
                    bundle.putString(key, savedStateHandle.get<String>(key)!!)
                }
                savedStateHandle.get<Boolean>(key) is Boolean -> {
                    bundle.putBoolean(key, savedStateHandle.get<Boolean>(key)!!)
                }
                savedStateHandle.get<Parcelable>(key) is Parcelable -> {
                    bundle.putParcelable(key, savedStateHandle.get<Parcelable>(key))
                }
                else -> Log.e("SavedStateHandleHelper", "Not known object class for $key")
            }
        }
        return bundle
    }
}