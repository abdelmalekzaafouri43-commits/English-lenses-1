package com.example.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.model.Quiz

@Composable
fun QuizPlayerScreen(quiz: Quiz, onQuizComplete: (Int) -> Unit) {
    var currentQuestionIndex by remember { mutableIntStateOf(0) }
    var score by remember { mutableIntStateOf(0) }
    
    if (currentQuestionIndex < quiz.questions.size) {
        val question = quiz.questions[currentQuestionIndex]
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Question ${currentQuestionIndex + 1} / ${quiz.questions.size}", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(16.dp))
            Text(question.text, style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(16.dp))
            question.options.forEachIndexed { index, option ->
                Button(
                    onClick = {
                        if (index == question.correctAnswerIndex) score++
                        currentQuestionIndex++
                    },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                ) {
                    Text(option)
                }
            }
        }
    } else {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Quiz Complete!", style = MaterialTheme.typography.headlineMedium)
            Text("Your score: $score / ${quiz.questions.size}", style = MaterialTheme.typography.bodyLarge)
            Button(onClick = { onQuizComplete(score) }) { Text("Back to Quizzes") }
        }
    }
}
