package com.example.baqiala.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM notes  ORDER BY year DESC, month DESC ,day DESC,hour DESC, minute DESC, second DESC")
    fun getAllNotes(): LiveData<MutableList<Note>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)
    @Query("DELETE FROM notes WHERE id = :itemId")
  suspend  fun deleteItemById(itemId: Int)


}
