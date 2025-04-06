package com.example.spearandroidassesment.ui.screens

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.spearandroidassesment.viewmodel.FollowListViewModel



import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.saveable.rememberSaveable
import com.example.spearandroidassesment.model.GithubUser
import com.example.spearandroidassesment.ui.components.UserListItemSkeleton
import com.example.spearandroidassesment.ui.components.UserProfileCard
import com.example.spearandroidassesment.ui.components.UserProfileSkeleton
import com.google.gson.Gson

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FollowScreen(user: GithubUser, navController: NavController) {
    val viewModel: FollowListViewModel = viewModel()

    var selectedTab by rememberSaveable { mutableStateOf(0) }

    val tabs = listOf("Followers", "Following")

    // Load user only once when user changes
    LaunchedEffect(user.login) {
        viewModel.clearCurrentUser()

        if (user.bio == null || user.followers == 0) {
            viewModel.loadUser(user.login)
        } else {
            viewModel.setCurrentUser(user)
        }
    }

//Load followers/following when tab changes
    LaunchedEffect(selectedTab, user.login) {
        if (selectedTab == 0) {
            viewModel.loadFollowers(user.login)
        } else {
            viewModel.loadFollowing(user.login)
        }
    }

    val followers by viewModel.followers.collectAsState()
    val following by viewModel.following.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text(user.name ?: user.login) },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            }
        )

        val currentUser by viewModel.currentUser.collectAsState()

        if (currentUser != null) {
            UserProfileCard(user = currentUser!!)
        } else {
            UserProfileSkeleton()
        }

        // Show current user card (no need to load it again!)
        //UserProfileCard(user = user)

        TabRow(selectedTabIndex = selectedTab) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { Text(title) }
                )
            }
        }

        when {
            isLoading -> LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                items(10) { // show 10 shimmer items
                    UserListItemSkeleton()
                }
            }
            error != null -> Text("Error: $error", Modifier.padding(16.dp))
            else -> {
                val list = if (selectedTab == 0) followers else following
                LazyColumn(Modifier.fillMaxSize().padding(16.dp)) {
                    items(list) { user ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    val userJson = Uri.encode(Gson().toJson(user))
                                    navController.navigate("follow/$userJson")
                                }
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            AsyncImage(
                                model = user.avatarUrl,
                                contentDescription = null,
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(Modifier.width(12.dp))
                            Text(user.login, style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }
        }
    }
}


