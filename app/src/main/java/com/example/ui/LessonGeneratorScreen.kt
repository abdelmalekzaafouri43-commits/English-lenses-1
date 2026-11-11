package com.example.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun LessonGeneratorScreen(viewModel: LessonGeneratorViewModel = LessonGeneratorViewModel()) {
    var topic by remember { mutableStateOf("") }
    var level by remember { mutableStateOf("9th form") }
    val content by viewModel.uiState.collectAsStateWithLifecycle()
    val loading by viewModel.loading.collectAsStateWithLifecycle()

    Column(modifier = Modifier.padding(16.dp).verticalScroll(rememberScrollState())) {
        Text("AI Lesson Generator", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        
        OutlinedTextField(
            value = topic,
            onValueChange = { topic = it },
            label = { Text("Topic") },
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        OutlinedTextField(
            value = level,
            onValueChange = { level = it },
            label = { Text("Level") },
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Button(
            onClick = { viewModel.generateLesson(topic, level) },
            modifier = Modifier.fillMaxWidth(),
            enabled = !loading && topic.isNotBlank()
        ) {
            if (loading) CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary)
            else Text("Generate Lesson")
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        if (content.isNotBlank()) {
            Text(content, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
