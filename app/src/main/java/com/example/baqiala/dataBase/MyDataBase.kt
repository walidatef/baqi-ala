package com.example.baqiala.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.baqiala.data.Note
import com.example.baqiala.data.NoteDao

@Database(entities = [Note::class], version = 1)
abstract class MyDatabase : RoomDatabase() {
    abstract fun noteDoa(): NoteDao

    companion object {
        @Volatile
        private var INSTANCE: MyDatabase? = null

        fun getDatabase(context: Context): MyDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MyDatabase::class.java,
                    "my_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
