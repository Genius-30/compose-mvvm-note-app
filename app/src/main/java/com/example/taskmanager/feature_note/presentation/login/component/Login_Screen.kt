package com.example.taskmanager.feature_note.presentation.login.component

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.taskmanager.R
import com.example.taskmanager.feature_note.presentation.util.Screen
import com.example.taskmanager.ui.theme.DarkGrayCustom

@Composable
fun LoginScreen(navController: NavController) {

    val customTextSelectionColors = TextSelectionColors(
        handleColor = Color(0xFFFFC107),
        backgroundColor = Color.LightGray
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(if (isSystemInDarkTheme()) DarkGrayCustom else Color.White),
        contentAlignment = Alignment.Center
    ) {

        var username by remember {
            mutableStateOf("")
        }
        var password by remember {
            mutableStateOf("")
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {
                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .padding(4.dp),
                    shape = RoundedCornerShape(10.dp),
                    label = {
                        Text(text = "Enter username")
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Person,
                            contentDescription = null,
                            tint = if (isSystemInDarkTheme()) Color.White else DarkGrayCustom
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = if (isSystemInDarkTheme()) Color.White else DarkGrayCustom,
                        unfocusedIndicatorColor = if (isSystemInDarkTheme()) Color.LightGray else Color.DarkGray,
                        cursorColor = if (isSystemInDarkTheme()) Color.White else DarkGrayCustom,
                        focusedLabelColor = if (isSystemInDarkTheme()) Color.White else DarkGrayCustom,
                        unfocusedLabelColor = if (isSystemInDarkTheme()) Color.Gray else Color.Gray,
                        focusedTextColor = if (isSystemInDarkTheme()) Color.White else DarkGrayCustom
                    )
                )
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .padding(4.dp),
                    shape = RoundedCornerShape(10.dp),
                    label = {
                        Text(text = "Enter password")
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Lock,
                            contentDescription = null,
                            tint = if (isSystemInDarkTheme()) Color.White else DarkGrayCustom
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = if (isSystemInDarkTheme()) Color.White else DarkGrayCustom,
                        unfocusedIndicatorColor = if (isSystemInDarkTheme()) Color.LightGray else Color.DarkGray,
                        cursorColor = if (isSystemInDarkTheme()) Color.White else DarkGrayCustom,
                        focusedLabelColor = if (isSystemInDarkTheme()) Color.White else DarkGrayCustom,
                        unfocusedLabelColor = if (isSystemInDarkTheme()) Color.Gray else Color.Gray,
                        focusedTextColor = if (isSystemInDarkTheme()) Color.White else DarkGrayCustom
                    )
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .height(TextFieldDefaults.MinHeight),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonColors(
                    containerColor = Color(0xFFFFC107),
                    contentColor = DarkGrayCustom,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.DarkGray
                ),
                onClick = { navController.navigate(Screen.NotesScreen.route) }
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Login to continue",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        painter = painterResource(id = R.drawable._115224_arrow_arrow_left_right_icon),
                        contentDescription = null,
                        modifier = Modifier.size(25.dp)
                    )
                }
            }
        }
    }
}