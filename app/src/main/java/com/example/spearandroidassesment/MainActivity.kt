package com.example.spearandroidassesment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.spearandroidassesment.ui.screens.SearchUserScreen
import com.example.spearandroidassesment.ui.theme.SpearAndroidAssesmentTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SpearAndroidAssesmentTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SearchUserScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
