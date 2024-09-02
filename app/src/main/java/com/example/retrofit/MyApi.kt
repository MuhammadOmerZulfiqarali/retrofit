package com.example.retrofit

import org.w3c.dom.Comment


import retrofit2.Call
import retrofit2.http.GET

interface MyApi {
    @GET("comments")
    fun getComments(): Call<List<Comments>>
}
