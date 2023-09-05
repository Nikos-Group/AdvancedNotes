package com.ariete.notes.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.ariete.notes.R
import com.ariete.notes.activities.MainActivity
import com.ariete.notes.databinding.FragmentNewNoteBinding
import com.ariete.notes.model.Note
import com.ariete.notes.helper.toast
import com.ariete.notes.viewmodel.NoteViewModel
import com.google.android.material.snackbar.Snackbar

class NewNoteFragment : Fragment(R.layout.fragment_new_note) {

    private var _binding: FragmentNewNoteBinding? = null

    private val binding get() = _binding!!

    private lateinit var noteViewModel: NoteViewModel
    private lateinit var mView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        /**
            * This method() set a flag, specifying for a presence
            * of an options menu in a fragment.
            * Flag set to true -> the method onCreateOptionsMenu is called
            * ------------------------------------------------------------
            * Этот метод устанавливает флаг,
            * указывающий на наличие меню опций в фрагменте.
            * Флаг установлен в true ->
            * вызывается метод onCreateOptionsMenu(),
        */
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentNewNoteBinding.inflate(
            inflater,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        noteViewModel = (activity as MainActivity).noteViewModel
        mView = view
    }

    private fun saveNote(view: View) {
        val noteTitle = binding.edNoteTitle.text.toString().trim()
        val noteBody = binding.edNoteBody.text.toString().trim()

        if (noteTitle.isNotEmpty()) {
            val note = Note(
                0,
                noteTitle,
                noteBody
            )

            noteViewModel.addNote(note)

            Snackbar.make(
                view,
                "Note saved successfully",
                Snackbar.LENGTH_SHORT
            ).show()

            view.findNavController().navigate(
                R.id.action_newNoteFragment_to_homeFragment
            )
        } else {
            activity?.toast("Please enter note title!")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.new_note_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save_menu -> {
                saveNote(mView)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}