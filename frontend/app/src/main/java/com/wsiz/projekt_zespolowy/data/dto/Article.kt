package com.wsiz.projekt_zespolowy.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
    val content: String,
    val title: String,
    val imageURL: String,
    val id: Int = -1
): Parcelable