package com.ariete.advancednotes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ariete.advancednotes.repository.NoteRepository
import com.ariete.advancednotes.model.Note
import kotlinx.coroutines.launch

class NoteViewModel(
    val app: Application,
    private val noteRepository: NoteRepository
) : AndroidViewModel(app) {

    /**
        * 0 - note creation;
        * 1 - change after save.
    */
    var status: Int = 0

    val dataCurrentNote: MutableLiveData<Note> = MutableLiveData()

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