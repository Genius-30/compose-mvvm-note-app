package com.example.taskmanager.feature_note.presentation.add_edit_note.components

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.taskmanager.feature_note.domain.model.Note
import com.example.taskmanager.feature_note.presentation.add_edit_note.AddEditNoteActions
import com.example.taskmanager.feature_note.presentation.add_edit_note.AddEditNoteViewModel
import com.example.taskmanager.ui.theme.DarkGrayCustom
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun AddEditNoteScreen(
    navController: NavController,
    noteColor: Int,
    viewModel: AddEditNoteViewModel = hiltViewModel()
) {
    val titleState = viewModel.noteTitle.value
    val contentState = viewModel.noteContent.value

    val snackbarHostState = remember { SnackbarHostState() }

    val noteBackgroundAnimatable = remember {
        Animatable(
            Color(if (noteColor != -1) noteColor else viewModel.noteColor.value)
        )
    }
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        viewModel.actionFlow.collectLatest { event ->
            when (event) {
                is AddEditNoteViewModel.UiAction.SaveNote -> {
                    navController.navigateUp()
                }

                is AddEditNoteViewModel.UiAction.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(noteBackgroundAnimatable.value)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = DarkGrayCustom
                    )
                }
                IconButton(onClick = {
                    viewModel.onAction(AddEditNoteActions.SaveNote)
                }) {
                    Icon(
                        imageVector = Icons.Filled.Done,
                        contentDescription = "Save note",
                        tint = DarkGrayCustom
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                TransparentHintTextField(
                    text = titleState.text,
                    hint = "Title...",
                    onValueChange = {
                        viewModel.onAction(AddEditNoteActions.EnteredTitle(it))
                    },
                    singleLine = true,
                    textStyle = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.SemiBold),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(modifier = Modifier.wrapContentHeight(), verticalAlignment = Alignment.Top) {
                    TransparentHintTextField(
                        text = contentState.text,
                        hint = "Note",
                        onValueChange = {
                            viewModel.onAction(AddEditNoteActions.EnteredContent(it))
                        },
                        textStyle = TextStyle(fontSize = 16.sp, lineHeight = 22.sp),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Note.noteColors.forEach { color ->
                    val colorInt = color.toArgb()
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .shadow(10.dp, CircleShape)
                            .clip(CircleShape)
                            .background(color)
                            .border(
                                width = 2.dp,
                                color = if (viewModel.noteColor.value == colorInt) {
                                    if (isSystemInDarkTheme()) Color.White else DarkGrayCustom
                                } else Color.Transparent,
                                shape = CircleShape
                            )
                            .clickable {
                                scope.launch {
                                    noteBackgroundAnimatable.animateTo(
                                        targetValue = Color(colorInt),
                                        animationSpec = tween(
                                            durationMillis = 500
                                        )
                                    )
                                }
                                viewModel.onAction(AddEditNoteActions.ChangeColor(colorInt))
                            }
                    )
                }
            }
        }
    }
}
