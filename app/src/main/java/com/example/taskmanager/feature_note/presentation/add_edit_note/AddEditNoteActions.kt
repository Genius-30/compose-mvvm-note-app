package com.example.taskmanager.feature_note.presentation.add_edit_note

sealed class AddEditNoteActions {
    data class EnteredTitle(val value: String): AddEditNoteActions()
    data class EnteredContent(val value: String): AddEditNoteActions()
    data class ChangeColor(val color: Int): AddEditNoteActions()
    data object SaveNote: AddEditNoteActions()
}