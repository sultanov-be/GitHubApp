package com.example.githubapp.ui

import android.util.Log
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubapp.data.model.ReposList
import com.example.githubapp.data.repository.MainRepository
import com.example.githubapp.utils.DispatcherProvider
import com.example.githubapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository,
    private val dispatcher: DispatcherProvider
) : ViewModel(), LifecycleObserver {

    sealed class RepoEvent {
        class Success(val result: ReposList) : RepoEvent()

        class Failure(val result: String) : RepoEvent()
        object Loading : RepoEvent()
        object Empty : RepoEvent()
    }

    private val _conversion = MutableStateFlow<RepoEvent>(RepoEvent.Empty)
    val conversion: StateFlow<RepoEvent> = _conversion

    fun getInfo() {
        viewModelScope.launch(dispatcher.io) {
            _conversion.value = RepoEvent.Loading
            when (val reposResponse = repository.getRepos()) {
                is Resource.Error -> {
                    _conversion.value = RepoEvent.Failure("Error occured")
                    Log.i("RetrofitAAA", "Error")
                }
                is Resource.Success -> {
                    RepoEvent.Success(reposResponse.data!!)
                    Log.i("RetrofitAAA", "Error")
                }
            }
        }
    }
}
