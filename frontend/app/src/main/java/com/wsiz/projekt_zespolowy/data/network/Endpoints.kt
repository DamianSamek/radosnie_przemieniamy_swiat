package com.wsiz.projekt_zespolowy.data.network

object Endpoints {

    const val BASE_URL = "https://radosnie-przemieniamy-swiat.herokuapp.com/"

    // Params
    const val ID_PARAM = "id"
    const val PAGE_NUMBER_PARAM = "page_number"

    // Endpoints

    private const val POST = "post"
    const val CREATE_POST = "$POST/create"
    const val UPDATE_POST = "$POST/update"
    const val GET_ALL_POSTS = "$POST/list"
    const val GET_USER_POSTS = "$POST/get-by-user/{$ID_PARAM}"
    const val LIKE_POST = "$POST/like/{$ID_PARAM}"

    private const val ARTICLE = "article"
    const val GET_ALL_ARTICLES = "$ARTICLE/list"

    const val LOGIN = "login"
}