package com.ariete.notes.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ariete.notes.repository.NoteRepository

class NoteViewModelProviderFactory(
    val app: Application,
    private val noteRepository: NoteRepository
) : ViewModelProvider.AndroidViewModelFactory(app) {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NoteViewModel(app, noteRepository) as T
        /**
            * as - это оператор "небезопасного" приведения
            * ----------------------------------------
            * as - it is an operator for unsafe cast.
        */
    }
}