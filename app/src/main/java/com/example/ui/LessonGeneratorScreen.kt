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
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(modifier = Modifier.padding(16.dp).verticalScroll(rememberScrollState())) {
        Text("AI Lesson Generator", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        
        OutlinedTextField(
            value = topic,
            onValueChange = { topic = it },
            label = { Text("Topic (e.g., Passive Voice)") },
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        OutlinedTextField(
            value = level,
            onValueChange = { level = it },
            label = { Text("Level (e.g., 9th form)") },
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Button(
            onClick = { viewModel.generateLesson(topic, level) },
            modifier = Modifier.fillMaxWidth(),
            enabled = uiState !is LessonUiState.Loading && topic.isNotBlank()
        ) {
            Text("Generate Lesson")
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        when (val state = uiState) {
            is LessonUiState.Loading -> CircularProgressIndicator()
            is LessonUiState.Streaming -> {
                Text("Generating...", style = MaterialTheme.typography.labelMedium)
                Text(state.text)
            }
            is LessonUiState.Success -> Text(state.text)
            is LessonUiState.Error -> Text("Error: ${state.message}", color = MaterialTheme.colorScheme.error)
            is LessonUiState.Idle -> {}
        }
    }
}
