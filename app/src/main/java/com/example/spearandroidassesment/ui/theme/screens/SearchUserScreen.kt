package com.example.spearandroidassesment.ui.screens

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.spearandroidassesment.ui.components.UserProfileCard
import com.example.spearandroidassesment.ui.components.UserProfileSkeleton
import com.example.spearandroidassesment.viewmodel.SearchUserViewModel
import com.google.gson.Gson

@Composable
fun SearchUserScreen(navController: NavController) {
    val viewModel: SearchUserViewModel = viewModel()

    var query by rememberSaveable { mutableStateOf("") }

    val user by viewModel.user.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp)
        ) {
            Text(
                text = "Search For A User On GitHub",
                style = MaterialTheme.typography.titleLarge
            )

            TextField(
                value = query,
                onValueChange = { query = it },
                placeholder = { Text("Enter A GitHub Username") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        focusManager.clearFocus()
                        viewModel.searchUser(query)
                    }
                )
            )

            when {
                isLoading -> UserProfileSkeleton()
                error != null -> Text(
                    text = error ?: "Something went wrong",
                    color = MaterialTheme.colorScheme.error
                )
                user != null -> {
                    UserProfileCard(
                        user = user!!,
                        onClick = {
                            val userJson = Uri.encode(Gson().toJson(user))
                            navController.navigate("follow/$userJson")
                        }
                    )
                }
            }
        }
    }
}

