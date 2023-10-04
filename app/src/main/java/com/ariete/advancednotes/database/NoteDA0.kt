package com.ariete.advancednotes.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ariete.advancednotes.model.Note

@Dao
interface NoteDA0 {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    /**
        * onConflict - a strategy for a fight with a conflict.
        *
        * REPLACE - for replacing existing strings with new ones.
        * -------------------------------------------------------
        * onConflict - стратегия борьбы с конфликтом.
        *
        * REPLACE - замена существующих строк новыми.
     */
    suspend fun addNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("SELECT * FROM notes ORDER BY id DESC")
    fun getAllNotes(): LiveData<List<Note>>

    @Query("SELECT * FROM notes WHERE noteTitle LIKE :query OR noteBody LIKE :query")
    fun searchNotes(query: String?): LiveData<List<Note>>
}