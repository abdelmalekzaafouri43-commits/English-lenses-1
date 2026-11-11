package com.example.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.ai.*
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout

private const val TAG = "LessonGenViewModel"

sealed interface LessonUiState {
    data object Idle : LessonUiState
    data object Loading : LessonUiState
    data class Streaming(val text: String) : LessonUiState
    data class Success(val text: String) : LessonUiState
    data class Error(val message: String) : LessonUiState
}

class LessonGeneratorViewModel : ViewModel() {
    private val model by lazy {
        Firebase.ai.generativeModel("gemini-3.8-flash")
    }

    private val _uiState = MutableStateFlow<LessonUiState>(LessonUiState.Idle)
    val uiState: StateFlow<LessonUiState> = _uiState.asStateFlow()

    fun generateLesson(topic: String, level: String) {
        viewModelScope.launch {
            _uiState.value = LessonUiState.Loading
            val accumulatedText = StringBuilder()
            try {
                val prompt = "Create a detailed English lesson about '$topic' for '$level' students. " +
                        "Include: reading passage, comprehension questions, vocabulary exercises, " +
                        "grammar explanations, language activities, writing tasks, and clear teacher answer keys."
                
                withTimeout(60_000L) {
                    model.generateContentStream(prompt).collect { chunk ->
                        val chunkText = chunk.text.orEmpty()
                        accumulatedText.append(chunkText)
                        _uiState.value = LessonUiState.Streaming(accumulatedText.toString())
                    }
                }
                
                if (accumulatedText.isNotBlank()) {
                    _uiState.value = LessonUiState.Success(accumulatedText.toString())
                } else {
                    _uiState.value = LessonUiState.Error("Model returned an empty response.")
                }
            } catch (e: TimeoutCancellationException) {
                Log.e(TAG, "Generation timed out", e)
                _uiState.value = LessonUiState.Error("Generation timed out. Please try again.")
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Log.e(TAG, "Lesson generation failed", e)
                _uiState.value = LessonUiState.Error(e.localizedMessage ?: "Lesson generation failed.")
            }
        }
    }
}
