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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.spearandroidassesment.ui.components.UserProfileCard
import com.example.spearandroidassesment.viewmodel.SearchUserViewModel
import com.google.gson.Gson

@Composable
fun SearchUserScreen(navController: NavController, modifier: Modifier = Modifier) {
    val viewModel: SearchUserViewModel = viewModel()

    var query by rememberSaveable { mutableStateOf("") }

    val user by viewModel.user.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = query,
            onValueChange = { query = it },
            placeholder = { Text("Enter GitHub username") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done // or ImeAction.Search
            ),
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { viewModel.searchUser(query) }) {
            Text("Search")
        }

        Spacer(modifier = Modifier.height(24.dp))

        when {
            isLoading -> {
                CircularProgressIndicator()
            }
            error != null -> {
                Text(text = error ?: "Something went wrong", color = MaterialTheme.colorScheme.error)
            }
            user != null -> {
                val u = user!!
                println("SYED: ${u}")
                UserProfileCard(
                    user = u,
                    onClick = {
                        val userJson = Uri.encode(Gson().toJson(u))
                        navController.navigate("follow/$userJson")
                    }
                )

            }
        }
    }
}
