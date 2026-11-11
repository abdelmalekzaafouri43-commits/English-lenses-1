package com.example.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.ai.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout

class LessonGeneratorViewModel : ViewModel() {
    private val model by lazy {
        Firebase.ai.generativeModel("gemini-3.8-flash")
    }

    private val _uiState = MutableStateFlow<String>("")
    val uiState: StateFlow<String> = _uiState

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    fun generateLesson(topic: String, level: String) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val prompt = "Create an English lesson about '$topic' for '$level' students. Include: reading passage, comprehension questions, vocabulary exercises, grammar explanations, language activities, writing tasks, and answer keys."
                val response = withTimeout(60_000L) {
                    model.generateContent(prompt)
                }
                _uiState.value = response.text ?: "No content generated."
            } catch (e: Exception) {
                _uiState.value = "Error generating lesson: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }
}
