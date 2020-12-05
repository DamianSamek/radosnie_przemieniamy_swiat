package com.wsiz.projekt_zespolowy.data.dto

data class Post(
    val description: String,
    val imageURL: String,
    val id: Int = -1
) {

    companion object {

        fun map(userPost: UserPost): Post {
            return Post(userPost.description, userPost.imageURL, userPost.id)
        }
    }
}