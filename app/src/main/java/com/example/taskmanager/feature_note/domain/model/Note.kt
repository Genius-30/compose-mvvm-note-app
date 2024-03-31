package com.example.taskmanager.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.taskmanager.ui.theme.BlueCustom
import com.example.taskmanager.ui.theme.CreamCustom
import com.example.taskmanager.ui.theme.GreenCustom
import com.example.taskmanager.ui.theme.PurpleCustom
import com.example.taskmanager.ui.theme.RedCustom
import com.example.taskmanager.ui.theme.YellowCustom

@Entity
data class Note(
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,
    @PrimaryKey val id: Int? = null
) {
    companion object {
        val noteColors = listOf(
            YellowCustom,
            CreamCustom,
            BlueCustom,
            GreenCustom,
            PurpleCustom
        )
    }
}

class InvalidNoteException(message: String): Exception(message)