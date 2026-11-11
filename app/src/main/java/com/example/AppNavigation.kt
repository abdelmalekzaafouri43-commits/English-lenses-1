package com.example

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ui.theme.EnglishLensesTheme
import com.example.ui.QuizScreen
import com.example.ui.QuizViewModel
import com.example.ui.LessonGeneratorScreen
import com.example.ui.PlaceholderScreen
import com.example.data.QuizRepository

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val screens = mapOf(
        Screen.Dashboard to Icons.Default.Dashboard,
        Screen.VideoStudio to Icons.Default.VideoCall,
        Screen.QuizStudio to Icons.Default.Edit,
        Screen.LessonGenerator to Icons.Default.List,
        Screen.WorksheetStudio to Icons.Default.Home,
        Screen.VoiceStudio to Icons.Default.Mic,
        Screen.PresentationMaker to Icons.Default.PlayArrow,
        Screen.Projects to Icons.Default.Person,
        Screen.Settings to Icons.Default.Settings
    )

    PermanentNavigationDrawer(
        drawerContent = {
            PermanentDrawerSheet(modifier = Modifier.width(240.dp)) {
                Text("English Lenses", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(16.dp))
                screens.forEach { (screen, icon) ->
                    NavigationDrawerItem(
                        icon = { Icon(icon, contentDescription = null) },
                        label = { Text(screen.title) },
                        selected = false,
                        onClick = { navController.navigate(screen) }
                    )
                }
            }
        }
    ) {
        Scaffold { innerPadding ->
            NavHost(navController = navController, startDestination = Screen.Dashboard, modifier = Modifier.padding(innerPadding)) {
                composable<Screen.Dashboard> { Text("Dashboard Screen") }
                composable<Screen.VideoStudio> { PlaceholderScreen("AI Video Studio") }
                composable<Screen.QuizStudio> { 
                    val repository = QuizRepository(com.google.firebase.firestore.FirebaseFirestore.getInstance())
                    val viewModel = QuizViewModel(repository)
                    QuizScreen(viewModel) 
                }
                composable<Screen.LessonGenerator> { LessonGeneratorScreen() }
                composable<Screen.WorksheetStudio> { PlaceholderScreen("Worksheet & Poster Studio") }
                composable<Screen.VoiceStudio> { PlaceholderScreen("Voice & Listening Studio") }
                composable<Screen.PresentationMaker> { PlaceholderScreen("Presentation Maker") }
                composable<Screen.Projects> { PlaceholderScreen("My Projects") }
                composable<Screen.Settings> { PlaceholderScreen("Settings") }
            }
        }
    }
}
