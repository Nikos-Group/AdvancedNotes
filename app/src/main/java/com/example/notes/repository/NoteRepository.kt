package com.example.notes.repository

import com.example.notes.db.NoteDatabase
import com.example.notes.model.Note

class NoteRepository(private val db: NoteDatabase) {

    /**
        * The suspend modifier defines a function which
        * can pause it's execution (for another function executing
        * and resume it after a certain period of time.
        * --------------------------------------------------------
        * Модификатор suspend определяет функцию,
        * которая может приостановить свое выполнение
        * (для того чтобы выполнилась другая функция)
        * и возобновить его через некоторый период времени.
    */

    suspend fun addNote(note: Note) = db.getNoteDao().addNote(note)

    suspend fun updateNote(note: Note) = db.getNoteDao().updateNote(note)

    suspend fun deleteNote(note: Note) = db.getNoteDao().deleteNote(note)

    fun getAllNotes() = db.getNoteDao().getAllNotes()

    fun searchNotes(query: String?) = db.getNoteDao().searchNotes(query)
}