package com.example.githubapp.data

import com.example.githubapp.data.model.ReposList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("users/{name}/repos")
    suspend fun getAllRepos(@Path("name") nickname: String): Response<ReposList>
}