package com.example.baqiala.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.baqiala.data.Note
import com.example.baqiala.dataBase.MyDatabase
import kotlinx.coroutines.launch

class MyViewModel(application: Application) : AndroidViewModel(application) {
    private val noteData = MyDatabase.getDatabase(application).noteDoa()
    val notes: LiveData<List<Note>> = noteData.getAllUsers()

    fun addUser(note: Note) {
        viewModelScope.launch {
            noteData.insertNote(note)
        }
    }

    fun deleteUser(note: Note) {
        viewModelScope.launch {
            noteData.deleteNote(note)
        }
    }
}
