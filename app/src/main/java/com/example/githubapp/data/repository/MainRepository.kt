package com.example.githubapp.data.repository

import com.example.githubapp.data.model.ReposList
import com.example.githubapp.utils.Resource

interface MainRepository {
    suspend fun getRepos(): Resource<ReposList>
}