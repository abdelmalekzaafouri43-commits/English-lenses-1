package com.example.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

import com.example.model.Quiz

@Composable
fun QuizScreen(viewModel: QuizViewModel) {
    val quizzes by viewModel.quizzes.collectAsStateWithLifecycle()
    var selectedQuiz by remember { mutableStateOf<Quiz?>(null) }

    if (selectedQuiz == null) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Quiz Studio", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn {
                items(quizzes) { quiz ->
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(8.dp),
                        onClick = { selectedQuiz = quiz }
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(quiz.title, style = MaterialTheme.typography.titleLarge)
                            Text(quiz.description)
                        }
                    }
                }
            }
        }
    } else {
        QuizPlayerScreen(quiz = selectedQuiz!!) {
            selectedQuiz = null
        }
    }
}
