package com.nikos_group.advancednotes.repository

import com.nikos_group.advancednotes.db.NoteDatabase
import com.nikos_group.advancednotes.model.Note

class NoteRepository(private val db: NoteDatabase) {

    /**
        * Модификатор suspend определяет функцию,
        * которая может приостановить свое выполнение
        * (для того чтобы выполнилась другая функция)
        * и возобновить его через некоторый период времени
     */

    suspend fun addNote(note: Note) = db.getNoteDao().addNote(note)
    suspend fun updateNote(note: Note) = db.getNoteDao().updateNote(note)
    suspend fun deleteNote(note: Note) = db.getNoteDao().deleteNote(note)
    fun getAllNotes() = db.getNoteDao().getAllNotes()
    fun searchNote(query: String?) = db.getNoteDao().searchNote(query)
}