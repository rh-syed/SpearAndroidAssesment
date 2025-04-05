package com.example.spearandroidassesment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spearandroidassesment.model.GithubUser
import com.example.spearandroidassesment.repository.GithubRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FollowListViewModel : ViewModel() {

    private val repository = GithubRepository()

    private val _followers = MutableStateFlow<List<GithubUser>>(emptyList())
    val followers: StateFlow<List<GithubUser>> = _followers

    private val _following = MutableStateFlow<List<GithubUser>>(emptyList())
    val following: StateFlow<List<GithubUser>> = _following

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun loadFollowers(username: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val response = repository.getFollowers(username)
                if (response.isSuccessful) {
                    _followers.value = response.body() ?: emptyList()
                } else {
                    _error.value = "Failed to fetch followers"
                }
            } catch (e: Exception) {
                _error.value = "Network error: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadFollowing(username: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val response = repository.getFollowing(username)
                if (response.isSuccessful) {
                    _following.value = response.body() ?: emptyList()
                } else {
                    _error.value = "Failed to fetch following"
                }
            } catch (e: Exception) {
                _error.value = "Network error: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
