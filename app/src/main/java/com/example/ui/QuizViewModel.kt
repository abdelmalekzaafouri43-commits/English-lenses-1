package com.example.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.QuizRepository
import com.example.model.Quiz
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.google.firebase.Firebase
import com.google.firebase.ai.*
import kotlinx.coroutines.withTimeout

class QuizViewModel(private val repository: QuizRepository) : ViewModel() {
    private val model by lazy {
        Firebase.ai.generativeModel("gemini-3.8-flash")
    }
    
    private val _quizzes = MutableStateFlow<List<Quiz>>(emptyList())
    val quizzes: StateFlow<List<Quiz>> = _quizzes

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    init {
        loadQuizzes()
    }

    private fun loadQuizzes() {
        viewModelScope.launch {
            _quizzes.value = repository.getQuizzes()
        }
    }

    fun generateQuiz(topic: String) {
        viewModelScope.launch {
            _loading.value = true
            try {
                // Simplified prompt for quiz generation
                val prompt = "Create a 5-question quiz about '$topic'. Return as JSON."
                val response = withTimeout(60_000L) {
                    model.generateContent(prompt)
                }
                // Handle parsing logic here (JSON)
            } catch (e: Exception) {
                // Log error
            } finally {
                _loading.value = false
            }
        }
    }
}
