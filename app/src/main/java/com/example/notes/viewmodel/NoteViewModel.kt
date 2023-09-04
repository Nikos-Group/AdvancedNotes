package com.example.notes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.model.Note
import com.example.notes.repository.NoteRepository
import kotlinx.coroutines.launch

class NoteViewModel(
    val app: Application,
    private val noteRepository: NoteRepository
) : AndroidViewModel(app) {

    /**
        * launch() - it is a function to launch an asynchronous operation.
        *
        * It creates a new coroutine and executes the function passed to it
        * in a separate thread
        * without blocking the main thread of program execution.
        *
        * viewModelScope - it is an element of architecture,
        * which is designed to manage
        * lifecycle of the ViewModel.
        *
        * It allows to create coroutines, which will run
        * within the life cycle of the ViewModel
        * which guarantees them to stop when the ViewModel is destroyed.
        * -----------------------------------------------------------------
        * launch() - это функция запуска асинхронной операции.
        *
        * Она создает новую корутину и
        * выполняет переданную ей функцию в отдельном потоке,
        * не блокируя главный поток.
        *
        * viewModelScope - это элемент архитектуры,
        * который предназначен для управления
        * жизненным циклом ViewModel.
        *
        * Он позволяет создавать корутины,
        * которые будут запускаться в пределах жизненного цикла ViewModel,
        * что гарантирует их остановку при уничтожении ViewModel.
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

    fun searchNotes(query: String?) = noteRepository.searchNotes(query)
}