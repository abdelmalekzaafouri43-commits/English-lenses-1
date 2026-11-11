package com.example.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PlaceholderScreen(title: String) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(title, style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Text("This module is under development and will be fully functional in the next update.", style = MaterialTheme.typography.bodyMedium)
    }
}
