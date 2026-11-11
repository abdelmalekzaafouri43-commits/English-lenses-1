package com.example.model

import kotlinx.serialization.Serializable

@Serializable
data class Question(
    val id: String = "",
    val text: String = "",
    val options: List<String> = emptyList(),
    val correctAnswerIndex: Int = 0
)

@Serializable
data class Quiz(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val questions: List<Question> = emptyList()
)
