package com.example.baqiala.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baqiala.data.Note
import com.example.baqiala.data.NoteDao
import com.example.baqiala.dataBase.MyDatabase
import kotlinx.coroutines.launch
import java.text.ParsePosition

class MyViewModel : ViewModel() {
    private lateinit var noteDoa: NoteDao
    lateinit var notes: LiveData<MutableList<Note>>


    fun getAllNotes(context: Context) {

        noteDoa = MyDatabase.getDatabase(context).noteDoa()

        viewModelScope.launch {
            // perform some long-running or asynchronous task
            notes = noteDoa.getAllNotes()

        }
    }

    fun deleteNote(context: Context,mNote: Note) {
        noteDoa = MyDatabase.getDatabase(context).noteDoa()
        viewModelScope.launch {
            noteDoa.deleteNote(mNote)
        }
    }


}