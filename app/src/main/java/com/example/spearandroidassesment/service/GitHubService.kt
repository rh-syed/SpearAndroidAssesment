package com.example.spearandroidassesment.service

import com.example.spearandroidassesment.model.GithubUser
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubService {
    @GET("users/{username}")
    suspend fun getUser(@Path("username") username: String): Response<GithubUser>

    @GET("users/{username}/followers")
    suspend fun getFollowers(@Path("username") username: String): Response<List<GithubUser>>

    @GET("users/{username}/following")
    suspend fun getFollowing(@Path("username") username: String): Response<List<GithubUser>>
}
