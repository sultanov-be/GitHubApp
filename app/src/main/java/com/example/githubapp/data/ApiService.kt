package com.example.githubapp.data

import com.example.githubapp.data.model.ReposList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface ApiService {

    @GET("users/sultanov-be/repos")
    suspend fun getAllRepos(): Response<ReposList>
}