package com.example.taskmanager.feature_note.presentation.splash.components

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.taskmanager.R
import com.example.taskmanager.feature_note.presentation.util.Screen
import com.example.taskmanager.ui.theme.DarkGrayCustom
import com.example.taskmanager.ui.theme.YellowCustom
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {

    LaunchedEffect(key1 = true) {
        delay(3000)
        navController.navigate(route = Screen.LoginScreen.route) {
            popUpTo(Screen.SplashScreen.route) {
                inclusive = true
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(if (isSystemInDarkTheme()) DarkGrayCustom else Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable._15166_text_note_icon),
                contentDescription = "App Logo",
                tint = if (isSystemInDarkTheme()) YellowCustom else Color(0xFFFFC107),
                modifier = Modifier.size(80.dp)
            )
            Text(
                text = "Task Manager",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = if (isSystemInDarkTheme()) Color.White else Color.Black
            )
        }
    }
}