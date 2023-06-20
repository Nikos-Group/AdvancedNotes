package com.example.notesversiontwo.repository

import com.example.notesversiontwo.db.NoteDatabase
import com.example.notesversiontwo.model.Note

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