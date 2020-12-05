package com.wsiz.projekt_zespolowy.data.dto

data class UserPostResponse(
    val description: String,
    val imageURL: String,
    val userId: Int,
    val user: User,
    val id: Int = -1
)