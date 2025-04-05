package com.example.spearandroidassesment.repository

import com.example.spearandroidassesment.model.GithubUser
import com.example.spearandroidassesment.service.RetrofitInstance
import retrofit2.Response

class GithubRepository {
    suspend fun searchUser(username: String): Response<GithubUser> {
        return RetrofitInstance.api.getUser(username)
    }
}
