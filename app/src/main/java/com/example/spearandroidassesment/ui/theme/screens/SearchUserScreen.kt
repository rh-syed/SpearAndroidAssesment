package com.example.spearandroidassesment.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.spearandroidassesment.viewmodel.SearchUserViewModel

@Composable
fun SearchUserScreen(modifier: Modifier = Modifier) {
    val viewModel: SearchUserViewModel = viewModel()

    var query by remember { mutableStateOf(TextFieldValue("")) }

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
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { viewModel.searchUser(query.text) }) {
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
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(vertical = 8.dp)
                        .clickable {
                            // TODO: Handle card click
                        },
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            AsyncImage(
                                model = u.avatarUrl,
                                contentDescription = null,
                                modifier = Modifier.size(100.dp),
                                contentScale = ContentScale.Crop
                            )

                            Text(
                                text = u.name ?: u.login,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = "@${u.login}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = u.bio ?: "No description",
                                style = MaterialTheme.typography.bodySmall
                            )
                            Text(
                                text = "${u.followers} followers • ${u.following} following",
                                style = MaterialTheme.typography.labelMedium
                            )
                        }
                    }
                }

            }
        }
    }
}
