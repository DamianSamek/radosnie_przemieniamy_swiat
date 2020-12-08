package com.wsiz.projekt_zespolowy.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Post(
    var description: String,
    val imageURL: String,
    val uuid: String,
    val id: Int = -1
) : Parcelable {

    companion object {

        fun map(userPost: UserPost): Post {
            return Post(userPost.description, userPost.imageURL, userPost.uuid, userPost.id)
        }
    }
}