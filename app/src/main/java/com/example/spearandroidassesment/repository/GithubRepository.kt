package com.example.spearandroidassesment.repository

import com.example.spearandroidassesment.model.GithubUser
import com.example.spearandroidassesment.service.RetrofitInstance
import retrofit2.Response

class GithubRepository {
    suspend fun searchUser(username: String): Response<GithubUser> {
        return RetrofitInstance.api.getUser(username)
    }

    suspend fun getFollowers(username: String): Response<List<GithubUser>> {
        return RetrofitInstance.api.getFollowers(username)
    }

    suspend fun getFollowing(username: String): Response<List<GithubUser>> {
        return RetrofitInstance.api.getFollowing(username)
    }
}
