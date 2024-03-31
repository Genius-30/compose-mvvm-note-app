package com.example.taskmanager.feature_note.presentation.util

sealed class Screen(val route: String){
    data object SplashScreen: Screen("splash_screen")
    data object LoginScreen: Screen("login_screen")
    data object NotesScreen: Screen("notes_screen")
    data object AddEditNoteScreen: Screen("sdd_edit_note_screen")
}