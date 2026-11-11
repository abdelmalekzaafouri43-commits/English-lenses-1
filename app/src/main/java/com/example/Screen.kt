package com.example

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.VideoCall
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

@Serializable
sealed class Screen(val route: String, val title: String) {
    @Serializable data object Dashboard : Screen("dashboard", "Dashboard")
    @Serializable data object VideoStudio : Screen("video_studio", "AI Video Studio")
    @Serializable data object QuizStudio : Screen("quiz_studio", "Quiz Studio")
    @Serializable data object LessonGenerator : Screen("lesson_generator", "AI Lesson Generator")
    @Serializable data object WorksheetStudio : Screen("worksheet_studio", "Worksheet & Poster Studio")
    @Serializable data object VoiceStudio : Screen("voice_studio", "Voice & Listening Studio")
    @Serializable data object PresentationMaker : Screen("presentation_maker", "Presentation Maker")
    @Serializable data object Projects : Screen("projects", "My Projects")
    @Serializable data object Settings : Screen("settings", "Settings")
}
