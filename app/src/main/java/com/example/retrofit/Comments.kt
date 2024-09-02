package com.example.retrofit

import retrofit2.http.Body

data class Comments(
    val body: String,
    val email: String,
    val id: Int,
    val name: String,
    val postId: Int
)
