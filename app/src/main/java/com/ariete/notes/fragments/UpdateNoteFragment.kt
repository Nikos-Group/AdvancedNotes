package com.ariete.notes.fragments

import android.app.AlertDialog
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
import com.ariete.notes.databinding.FragmentUpdateNoteBinding
import com.ariete.notes.helper.toast
import com.ariete.notes.model.Note
import com.ariete.notes.viewmodel.NoteViewModel

class UpdateNoteFragment : Fragment(R.layout.fragment_update_note) {

    private var _binding: FragmentUpdateNoteBinding? = null
    private val binding get() = _binding!!

    private lateinit var currentNote: Note
    private lateinit var noteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentUpdateNoteBinding.inflate(
            inflater,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        noteViewModel = (activity as MainActivity).noteViewModel

        currentNote = arguments?.get("note") as Note

        binding.edNoteTitleUpdate.setText(currentNote.noteTitle)
        binding.edNoteBodyUpdate.setText(currentNote.noteBody)

        binding.fabUpdate.setOnClickListener {

            val title = binding.edNoteTitleUpdate.text.toString().trim()
            val body = binding.edNoteBodyUpdate.text.toString().trim()

            if (title.isNotEmpty()) {
                val note = Note(currentNote.id, title, body)

                noteViewModel.updateNote(note)

                activity?.toast("Note updated!")

                view.findNavController().navigate(
                    R.id.action_updateNoteFragment_to_homeFragment
                )
            } else {
                activity?.toast("Please enter title name!")
            }
        }
    }

    private fun deleteNote() {
        AlertDialog.Builder(activity).apply {
            setTitle("Delete Note")
            setMessage("Are you sure want to delete this note?")

            setPositiveButton("DELETE") { _, _ ->
                noteViewModel.deleteNote(currentNote)

                view?.findNavController()?.navigate(
                    R.id.action_updateNoteFragment_to_homeFragment
                )
            }

            setNegativeButton("CANCEL", null)
        }.create().show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.delete_note_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete_menu -> deleteNote()
        }
        return super.onOptionsItemSelected(item)
    }
}