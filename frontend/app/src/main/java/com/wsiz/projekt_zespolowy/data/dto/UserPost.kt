package com.wsiz.projekt_zespolowy.data.dto

data class UserPost(
    val description: String,
    val imageURL: String,
    val userId: Int,
    val userName: String?,
    var likesCount: Int,
    var isLikedByMe: Boolean,
    val id: Int = -1
) {

    companion object {

        fun map(userPostResponse: UserPostResponse): UserPost {
            return UserPost(
                userPostResponse.description,
                userPostResponse.imageURL,
                userPostResponse.userId,
                userPostResponse.user.name,
                userPostResponse.likesCount,
                userPostResponse.isLikedByMe,
                userPostResponse.id
            )
        }
    }
}