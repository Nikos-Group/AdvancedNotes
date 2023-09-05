package com.ariete.notes.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.ariete.notes.databinding.ActivityMainBinding
import com.ariete.notes.db.NoteDatabase
import com.ariete.notes.repository.NoteRepository
import com.ariete.notes.viewmodel.NoteViewModel
import com.ariete.notes.viewmodel.NoteViewModelProviderFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    lateinit var noteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolBar)
        /**
            * The function setSupportActionBar() set the specified toolbar
            * as the main panel of application for MainActivity
            * ---------------------------------------------------------------------
            * Функция setSupportActionBar() устанавливает указанный toolbar
            * в качестве основной панели приложения
            * для MainActivity
        */

        setUpViewModel()
    }

    private fun setUpViewModel() {
        val noteRepository = NoteRepository(
            NoteDatabase(this)
        )

        val viewModelProviderFactory = NoteViewModelProviderFactory(
            application, noteRepository
        )

        noteViewModel = ViewModelProvider(
            this,
            viewModelProviderFactory
        )[NoteViewModel::class.java]
    }
}