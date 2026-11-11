package com.example.data

import com.example.model.Quiz
import com.example.model.Question
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
class QuizRepository(
    private val firestore: FirebaseFirestore
) {
    private val quizzesCollection = firestore.collection("quizzes")

    suspend fun getQuizzes(): List<Quiz> {
        val snapshot = quizzesCollection.get().await()
        return snapshot.toObjects(Quiz::class.java)
    }

    suspend fun saveQuiz(quiz: Quiz) {
        quizzesCollection.document(quiz.id).set(quiz).await()
    }
}
