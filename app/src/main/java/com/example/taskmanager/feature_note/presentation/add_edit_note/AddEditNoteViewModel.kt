package com.example.taskmanager.feature_note.presentation.add_edit_note

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.feature_note.domain.model.InvalidNoteException
import com.example.taskmanager.feature_note.domain.model.Note
import com.example.taskmanager.feature_note.domain.use_case.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _noteTitle = mutableStateOf(
        NoteTextFieldState()
    )
    val noteTitle: State<NoteTextFieldState> = _noteTitle

    private val _noteContent = mutableStateOf(
        NoteTextFieldState()
    )
    val noteContent: State<NoteTextFieldState> = _noteContent

    private val _noteColor = mutableIntStateOf(Note.noteColors.random().toArgb())
    val noteColor: State<Int> = _noteColor

    private val _actionFlow = MutableSharedFlow<UiAction>()
    val actionFlow = _actionFlow.asSharedFlow()

    private var currentNoteId: Int? = null

    init {
        savedStateHandle.get<Int>("noteId")?.let { noteId ->
            if (noteId != -1) {
                viewModelScope.launch {
                    noteUseCases.getNote(noteId)?.also { note ->
                        currentNoteId = note.id
                        _noteTitle.value = noteTitle.value.copy(
                            text = note.title
                        )
                        _noteContent.value = noteContent.value.copy(
                            text = note.content
                        )
                        _noteColor.intValue = note.color
                    }
                }
            }
        }
    }

    fun onAction(action: AddEditNoteActions) {
        when (action) {
            is AddEditNoteActions.EnteredTitle -> {
                _noteTitle.value = noteTitle.value.copy(
                    text = action.value
                )
            }

            is AddEditNoteActions.EnteredContent -> {
                _noteContent.value = noteContent.value.copy(
                    text = action.value
                )
            }

            is AddEditNoteActions.ChangeColor -> {
                _noteColor.intValue = action.color
            }

            is AddEditNoteActions.SaveNote -> {
                viewModelScope.launch {
                    try {
                        noteUseCases.addNote(
                            Note(
                                title = noteTitle.value.text,
                                content = noteContent.value.text,
                                timestamp = System.currentTimeMillis(),
                                color = noteColor.value,
                                id = currentNoteId
                            )
                        )
                        _actionFlow.emit(UiAction.SaveNote)
                    } catch (e: InvalidNoteException) {
                        _actionFlow.emit(
                            UiAction.ShowSnackbar(
                                message = e.message ?: "Empty note will not be saved"
                            )
                        )
                    }
                }
            }
        }
    }

    sealed class UiAction {
        data class ShowSnackbar(val message: String) : UiAction()
        data object SaveNote : UiAction()
    }
}