package com.ariete.advancednotes.repository

import com.ariete.advancednotes.database.NoteDatabase
import com.ariete.advancednotes.model.Note

class NoteRepository(private val db: NoteDatabase) {

    /**
        * The suspend modifier defines a function which
        * can pause it's execution (for another function executing)
        * and resume it after a certain period of time.
        * ---------------------------------------------------------
        * Модификатор suspend определяет функцию,
        * которая может приостановить свое выполнение
        * (для того чтобы выполнилась другая функция)
        * и возобновить его через некоторый период времени.
     */

    suspend fun addNote(note: Note) = db.getNoteDA0().addNote(note)

    suspend fun updateNote(note: Note) = db.getNoteDA0().updateNote(note)

    suspend fun deleteNote(note: Note) = db.getNoteDA0().deleteNote(note)

    fun getAllNotes() = db.getNoteDA0().getAllNotes()

    fun searchNotes(query: String?) = db.getNoteDA0().searchNotes(query)
}