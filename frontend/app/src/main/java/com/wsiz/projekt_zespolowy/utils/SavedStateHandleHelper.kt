package com.wsiz.projekt_zespolowy.utils

import android.os.Bundle
import android.util.Log
import androidx.core.os.bundleOf
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgs

object SavedStateHandleHelper {

    inline fun <reified T: NavArgs> safeArgs(savedStateHandle: SavedStateHandle): T {
        return T::class.java.getMethod("fromBundle", Bundle::class.java)
            .invoke(null, toBundle(savedStateHandle)) as T
    }

    fun toBundle(savedStateHandle: SavedStateHandle): Bundle {
        val bundle = bundleOf()
        for (key in savedStateHandle.keys()) {
            when {
                savedStateHandle.get<Int>(key) != null -> {
                    bundle.putInt(key, savedStateHandle.get<Int>(key) as Int)
                }
                savedStateHandle.get<String>(key) != null -> {
                    bundle.putString(key, savedStateHandle.get<String>(key) as String)
                }
                savedStateHandle.get<Boolean>(key) != null -> {
                    bundle.putBoolean(key, savedStateHandle.get<Boolean>(key) as Boolean)
                }
                else -> Log.e("SavedStateHandleHelper", "Not known object class for $key")
            }
        }
        return bundle
    }
}