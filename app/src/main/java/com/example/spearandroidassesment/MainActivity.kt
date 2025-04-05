package com.example.spearandroidassesment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.spearandroidassesment.model.GithubUser
import com.example.spearandroidassesment.ui.screens.FollowScreen
import com.example.spearandroidassesment.ui.screens.SearchUserScreen
import com.example.spearandroidassesment.ui.theme.SpearAndroidAssesmentTheme
import com.google.gson.Gson

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SpearAndroidAssesmentTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "search",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("search") {
                            SearchUserScreen(navController = navController)
                        }
                        composable("follow/{userJson}") { backStackEntry ->
                            val userJson = backStackEntry.arguments?.getString("userJson") ?: return@composable
                            val user = Gson().fromJson(userJson, GithubUser::class.java)
                            FollowScreen(user = user, navController = navController)
                        }
                    }
                }
            }
        }
    }
}
