package com.example.taskmanager.feature_note.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.taskmanager.feature_note.presentation.add_edit_note.components.AddEditNoteScreen
import com.example.taskmanager.feature_note.presentation.login.component.LoginScreen
import com.example.taskmanager.feature_note.presentation.notes.components.NotesScreen
import com.example.taskmanager.feature_note.presentation.splash.components.SplashScreen
import com.example.taskmanager.feature_note.presentation.util.Screen
import com.example.taskmanager.ui.theme.StartupScreenTheme
import com.example.taskmanager.ui.theme.TaskManagerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = Screen.SplashScreen.route
            ) {
                composable(route = Screen.SplashScreen.route) {
                    StartupScreenTheme {
                        SplashScreen(navController = navController)
                    }
                }
                composable(route = Screen.LoginScreen.route) {
                    StartupScreenTheme {
                        LoginScreen(navController = navController)
                    }
                }
                composable(route = Screen.NotesScreen.route) {
                    TaskManagerTheme {
                        NotesScreen(navController = navController)
                    }
                }
                composable(
                    route = Screen.AddEditNoteScreen.route +
                            "?noteId={noteId}&noteColor={noteColor}",
                    arguments = listOf(
                        navArgument(
                            name = "noteId"
                        ) {
                            type = NavType.IntType
                            defaultValue = -1
                        },
                        navArgument(
                            name = "noteColor"
                        ) {
                            type = NavType.IntType
                            defaultValue = -1
                        }
                    )
                ) {
                    val color = it.arguments?.getInt("noteColor") ?: -1
                    TaskManagerTheme {
                        AddEditNoteScreen(
                            navController = navController,
                            noteColor = color
                        )
                    }
                }
            }
        }
    }
}
