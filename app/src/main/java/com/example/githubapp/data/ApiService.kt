package com.example.githubapp.data

import com.example.githubapp.data.model.ReposList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface ApiService {

    @Headers(
        "Accept: application/vnd.github+json",
        "Authorization: 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",
        "X-GitHub-Api-Version: 2022-11-28",
    )
    @GET("users/sultanov-be/repos")
    suspend fun getAllRepos(): Response<ReposList>
}