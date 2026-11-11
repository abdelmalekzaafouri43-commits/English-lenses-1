package com.example.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.model.Question
import com.example.model.Quiz
import java.util.UUID

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.platform.LocalContext
import com.example.util.ProjectFileUtils
import java.io.OutputStream

@Composable
fun QuizEditorScreen(quiz: Quiz, onSave: (Quiz) -> Unit) {
    var title by remember { mutableStateOf(quiz.title) }
    var questions by remember { mutableStateOf(quiz.questions) }
    val context = LocalContext.current

    val exportLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument("application/json")
    ) { uri ->
        uri?.let {
            context.contentResolver.openOutputStream(it)?.use { outputStream ->
                outputStream.write(ProjectFileUtils.encodeQuiz(quiz).toByteArray())
            }
        }
    }

    val importLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri ->
        uri?.let {
            context.contentResolver.openInputStream(it)?.use { inputStream ->
                val jsonString = inputStream.bufferedReader().use { reader -> reader.readText() }
                val importedQuiz = ProjectFileUtils.decodeQuiz(jsonString)
                title = importedQuiz.title
                questions = importedQuiz.questions
            }
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Quiz Title") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            Button(onClick = { exportLauncher.launch("${title.ifBlank { "quiz" }}.json") }) { Text("Export") }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { importLauncher.launch(arrayOf("application/json")) }) { Text("Import") }
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(modifier = Modifier.weight(1f)) {
            itemsIndexed(questions) { index, question ->
                OutlinedTextField(value = question.text, onValueChange = { questions[index].text = it }, label = { Text("Question ${index + 1}") }, modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
        Button(onClick = { 
            quiz.title = title
            quiz.questions = questions
            onSave(quiz) 
        }, modifier = Modifier.fillMaxWidth()) { Text("Save Quiz") }
    }
}
