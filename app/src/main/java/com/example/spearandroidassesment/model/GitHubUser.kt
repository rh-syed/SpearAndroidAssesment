package com.example.spearandroidassesment.model

import com.google.gson.annotations.SerializedName

data class GithubUser(
    val login: String,
    val name: String?,
    @SerializedName("avatar_url") val avatarUrl: String,
    val bio: String?,
    @SerializedName("followers")
    val followers: Int,
    @SerializedName("following")
    val following: Int
)
