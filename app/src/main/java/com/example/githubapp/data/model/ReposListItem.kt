package com.example.githubapp.data.model

data class ReposListItem(
    val name: String,
    val html_url: String,
    val owner: Owner,
)