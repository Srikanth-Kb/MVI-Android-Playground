package com.playground.mvi.api

import androidx.lifecycle.LiveData
import com.playground.mvi.model.BlogPost
import com.playground.mvi.model.User
import com.playground.mvi.util.GenericApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("placeholder/user/{userId}")
    fun getUser(
        @Path("userId") userId: String
    ):LiveData<GenericApiResponse<User>>

    @GET("placeholder/blogs")
    fun getBlogPosts() : LiveData<GenericApiResponse<List<BlogPost>>>

}