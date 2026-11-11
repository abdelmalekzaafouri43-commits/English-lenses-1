package com.example.model

import kotlinx.serialization.Serializable

@Serializable
data class Question(
    var id: String = "",
    var text: String = "",
    var options: MutableList<String> = mutableListOf("","","",""),
    var correctAnswerIndex: Int = 0
)

@Serializable
data class Quiz(
    var id: String = "",
    var title: String = "",
    var description: String = "",
    var questions: MutableList<Question> = mutableListOf()
)
