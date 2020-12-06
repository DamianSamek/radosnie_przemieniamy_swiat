package com.wsiz.projekt_zespolowy.data.dto

data class EditPost(
    var description: String,
    val id: Int = -1
) {

    companion object {
        fun map(post: Post) = EditPost(post.description, post.id)
    }
}