package com.example.baqiala.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    private val id: Int = 0,
    val name: String,
    val day: Int,
    var month: Int,
    var year: Int,

    var hour: Int,
    var minute: Int
)