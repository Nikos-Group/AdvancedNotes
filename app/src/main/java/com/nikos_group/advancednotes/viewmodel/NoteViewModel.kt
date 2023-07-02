package com.nikos_group.advancednotes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.nikos_group.advancednotes.model.Note
import com.nikos_group.advancednotes.repository.NoteRepository
import kotlinx.coroutines.launch

class NoteViewModel(
    val app: Application,
    private val noteRepository: NoteRepository
) : AndroidViewModel(app) {

    /**
        * launch() - это функция запуска асинхронной операции
        *
        * Она создает новый корутин и
        * выполняет переданную ей функцию в отдельном потоке,
        * не блокируя основной поток выполнения программы
        *
        * -------------------------------------------------
        *
        * viewModelScope - это элемент архитектуры,
        * который предназначен для управления
        * жизненным циклом ViewModel
        *
        * Он позволяет создавать корутины,
        * которые будут запускаться в пределах жизненного цикла ViewModel,
        * что гарантирует их остановку при уничтожении ViewModel
     */

    fun addNote(note: Note) = viewModelScope.launch {
        noteRepository.addNote(note)
    }

    fun updateNote(note: Note) = viewModelScope.launch {
        noteRepository.updateNote(note)
    }

    fun deleteNote(note: Note) = viewModelScope.launch {
        noteRepository.deleteNote(note)
    }

    fun getAllNotes() = noteRepository.getAllNotes()

    fun searchNote(query: String?) = noteRepository.searchNote(query)
}