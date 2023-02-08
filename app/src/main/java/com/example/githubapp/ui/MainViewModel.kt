package com.example.githubapp.ui

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubapp.data.model.ReposList
import com.example.githubapp.data.repository.MainRepository
import com.example.githubapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel(), LifecycleObserver {

    val reposAll: MutableLiveData<Resource<ReposList>> = MutableLiveData()

    init {
        getRepos()
    }

    private fun getRepos()=viewModelScope.launch {
        reposAll.postValue(Resource.Loading())
        val response = repository.getRepos()
        reposAll.postValue(response)
    }
}
