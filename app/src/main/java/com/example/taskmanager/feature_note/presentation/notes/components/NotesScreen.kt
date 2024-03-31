package com.example.taskmanager.feature_note.presentation.notes.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.taskmanager.feature_note.presentation.notes.NotesActions
import com.example.taskmanager.feature_note.presentation.notes.NotesViewModel
import com.example.taskmanager.feature_note.presentation.util.Screen
import com.example.taskmanager.ui.theme.DarkGrayCustom
import com.example.taskmanager.ui.theme.GrayCustom
import com.example.taskmanager.ui.theme.LightGrayCustom
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(
    navController: NavController,
    viewModel: NotesViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    var searchText by remember {
        mutableStateOf("")
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(if (isSystemInDarkTheme()) DarkGrayCustom else Color.White)
            .padding(10.dp),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState)},
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clip(RoundedCornerShape(50))
                    .shadow(8.dp)
                    .background(if (isSystemInDarkTheme()) DarkGrayCustom else Color.White),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = if (isSystemInDarkTheme()) GrayCustom else LightGrayCustom
                ),
                navigationIcon = {
                    Box(
                        modifier = Modifier.fillMaxHeight(),
                        contentAlignment = Alignment.Center
                    ) {
                        IconButton(
                            onClick = { navController.navigateUp() }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = if (isSystemInDarkTheme()) LightGrayCustom else DarkGrayCustom
                            )
                        }
                    }
                },
                title = {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        BasicTextField(
                            value = searchText,
                            onValueChange = { searchText = it },
                            modifier = Modifier
                                .fillMaxSize(),
                            textStyle = LocalTextStyle.current.copy(
                                color = if (isSystemInDarkTheme()) LightGrayCustom else DarkGrayCustom,
                                fontSize = 18.sp
                            ),
                            decorationBox = { innerTextField ->
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.CenterStart
                                ) {
                                    if (searchText.isEmpty()) {
                                        Text(
                                            text = "Search your notes...",
                                            fontSize = 18.sp,
                                            color = if (isSystemInDarkTheme()) Color.LightGray else GrayCustom
                                        )
                                    }
                                    innerTextField()
                                }
                            },
                            cursorBrush = SolidColor(if (isSystemInDarkTheme()) LightGrayCustom else DarkGrayCustom),
                            singleLine = true
                        )
                    }
                },
                actions = {
                    Box(
                        modifier = Modifier.fillMaxHeight(),
                        contentAlignment = Alignment.Center
                    ) {
                        IconButton(
                            onClick = {
                                viewModel.onActions(NotesActions.ToggleOrderSection)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Sort,
                                contentDescription = "Sort",
                                tint = if (isSystemInDarkTheme()) LightGrayCustom else DarkGrayCustom
                            )
                        }
                    }
                }
            )
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.AddEditNoteScreen.route)
                },
                shape = CircleShape,
                containerColor = if (isSystemInDarkTheme()) GrayCustom else LightGrayCustom
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add",
                    tint = if (isSystemInDarkTheme()) LightGrayCustom else DarkGrayCustom
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(if (isSystemInDarkTheme()) DarkGrayCustom else Color.White)
                .padding(padding)
        ) {
            AnimatedVisibility(
                visible = state.isOrderSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                OrderSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp),
                    noteOrder = state.noteOrder,
                    onOrderChange = {
                        viewModel.onActions(NotesActions.Order(it))
                    }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.notes.size) { index ->
                    val note = state.notes[index]
                    NoteItem(
                        note = note,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate(
                                    Screen.AddEditNoteScreen.route +
                                            "?noteId=${note.id}&noteColor=${note.color}"
                                )
                            },
                        onDeleteClick = {
                            viewModel.onActions(NotesActions.DeleteNote(note))
                            scope.launch {
                                val result = snackbarHostState.showSnackbar(
                                    message = "Note deleted",
                                    actionLabel = "Undo"
                                )
                                if (result == SnackbarResult.ActionPerformed) {
                                    viewModel.onActions(NotesActions.RestoreNote)
                                }
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}