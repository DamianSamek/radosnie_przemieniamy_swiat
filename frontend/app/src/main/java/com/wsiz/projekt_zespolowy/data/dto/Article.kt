package com.wsiz.projekt_zespolowy.data.dto

data class Article(
    val content: String,
    val title: String,
    val imageURL: String,
    val id: Int = -1
)