package com.example.githubapp.repository

import com.example.githubapp.data.model.ReposList
import com.example.githubapp.utils.Resource

interface MainRepository {
    suspend fun getRepos(): Resource<ReposList>
}