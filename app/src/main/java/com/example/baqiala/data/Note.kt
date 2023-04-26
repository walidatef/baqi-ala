package com.example.baqiala.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.Calendar

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val day: Int,
    var month: Int,
    var year: Int,
    var hour: Int,
    var minute: Int,
    var second: Int = 0,
    var addTime: Long = getAddTime()
)

fun getAddTime(): Long {

    val time = Calendar.getInstance()
    return time.timeInMillis
}