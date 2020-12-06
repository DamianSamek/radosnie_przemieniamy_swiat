package com.wsiz.projekt_zespolowy.data.dto

data class UserPostResponse(
    val description: String,
    val imageURL: String,
    val userId: Int,
    val user: User,
    val likesCount: Int,
    val isLikedByMe: Boolean,
    val uuid: String,
    val id: Int = -1
)