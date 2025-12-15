package com.example.pasteleriastore.remote

import com.example.pasteleriastore.model.Post
import retrofit2.http.GET

interface ApiService {

    @GET(value = "posts")
    suspend fun getPosts():List<Post>

}