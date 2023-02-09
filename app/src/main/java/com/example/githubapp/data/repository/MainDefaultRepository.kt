package com.example.githubapp.data.repository

import com.example.githubapp.data.ApiService
import com.example.githubapp.data.model.ReposList
import com.example.githubapp.utils.Resource
import javax.inject.Inject

class MainDefaultRepository @Inject constructor(
    private val api: ApiService
) : MainRepository {

    override suspend fun getRepos(nickname: String): Resource<ReposList> {
        return try {
            val response = api.getAllRepos(nickname)
            val result = response.body()
            if (response.isSuccessful && result != null) {
                Resource.Success(result)
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }

}