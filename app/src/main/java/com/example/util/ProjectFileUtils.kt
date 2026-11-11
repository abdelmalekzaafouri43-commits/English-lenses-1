package com.example.util

import com.example.model.Quiz
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object ProjectFileUtils {
    val json = Json { prettyPrint = true }

    fun encodeQuiz(quiz: Quiz): String {
        return json.encodeToString(quiz)
    }

    fun decodeQuiz(jsonString: String): Quiz {
        return json.decodeFromString(jsonString)
    }
}
