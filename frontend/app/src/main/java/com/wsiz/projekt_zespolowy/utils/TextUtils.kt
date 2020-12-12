package com.wsiz.projekt_zespolowy.utils

import android.text.Editable
import android.text.TextWatcher

object TextUtils {

    fun textWatcher(onTextChanged: (newText: String) -> Unit): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                onTextChanged(s?.toString() ?: "")
            }

            override fun afterTextChanged(s: Editable?) {}
        }
    }
}