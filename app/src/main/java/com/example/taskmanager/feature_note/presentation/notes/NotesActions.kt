package com.example.taskmanager.feature_note.presentation.notes

import com.example.taskmanager.feature_note.domain.model.Note
import com.example.taskmanager.feature_note.domain.util.NoteOrder

sealed class NotesActions {
    data class Order(val noteOrder: NoteOrder): NotesActions()
    data class DeleteNote(val note: Note): NotesActions()
    data object RestoreNote: NotesActions()
    data object ToggleOrderSection: NotesActions()
}