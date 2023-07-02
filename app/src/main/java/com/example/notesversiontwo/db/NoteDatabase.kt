package com.example.notesversiontwo.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notesversiontwo.model.Note

@Database(
    entities = [Note::class],
    version = 2,
    exportSchema = false
)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun getNoteDao(): NoteDao

    // Объявление объекта внутри класса отмечается ключевым словом companion
    companion object {
        @Volatile
        private var instance: NoteDatabase? = null

        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also {
                instance = it
            }
        }

        // метод для создания базы данных
        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            NoteDatabase::class.java,
            "note_db_2"
        ).build()
    }
}