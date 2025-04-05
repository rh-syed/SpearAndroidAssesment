package com.example.spearandroidassesment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spearandroidassesment.model.GithubUser
import com.example.spearandroidassesment.repository.GithubRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchUserViewModel : ViewModel() {

    private val repository = GithubRepository()

    // Holds the searched user (null means not found or not yet searched)
    private val _user = MutableStateFlow<GithubUser?>(null)
    val user: StateFlow<GithubUser?> = _user

    // Holds loading state
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    // Holds error message (null if no error)
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun searchUser(username: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            _user.value = null

            try {
                val response = repository.searchUser(username)
                if (response.isSuccessful) {
                    _user.value = response.body()
                } else if (response.code() == 404) {
                    _error.value = "User not found"
                } else {
                    _error.value = "Unexpected error: ${response.code()}"
                }
            } catch (e: Exception) {
                _error.value = "Network error: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}

