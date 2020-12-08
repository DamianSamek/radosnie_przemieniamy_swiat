package com.wsiz.projekt_zespolowy.utils

import android.os.Bundle
import android.util.Log
import androidx.core.os.bundleOf
import androidx.lifecycle.SavedStateHandle

object SavedStateHandleHelper {

    inline fun <reified T> safeArgs(savedStateHandleHelper: SavedStateHandle): T {
        return T::class.java.getMethod("fromBundle", Bundle::class.java)
            .invoke(null, toBundle(savedStateHandleHelper)) as T
    }

    fun toBundle(savedStateHandleHelper: SavedStateHandle): Bundle {
        val bundle = bundleOf()
        for (key in savedStateHandleHelper.keys()) {
            when {
                savedStateHandleHelper.get<Int>(key) != null -> {
                    bundle.putInt(key, savedStateHandleHelper.get<Int>(key) as Int)
                }
                savedStateHandleHelper.get<String>(key) != null -> {
                    bundle.putString(key, savedStateHandleHelper.get<String>(key) as String)
                }
                savedStateHandleHelper.get<Boolean>(key) != null -> {
                    bundle.putBoolean(key, savedStateHandleHelper.get<Boolean>(key) as Boolean)
                }
                else -> Log.e("SavedStateHandleHelper", "Not known object class for $key")
            }
        }
        return bundle
    }
}