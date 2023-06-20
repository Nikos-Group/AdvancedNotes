package com.example.notesversiontwo.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.SearchView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notesversiontwo.R
import com.example.notesversiontwo.activities.MainActivity
import com.example.notesversiontwo.adapters.NoteAdapter
import com.example.notesversiontwo.databinding.FragmentHomeBinding
import com.example.notesversiontwo.classes_for_menu.Identifiers
import com.example.notesversiontwo.model.Note
import com.example.notesversiontwo.viewmodel.NoteViewModel

class HomeFragment : Fragment(R.layout.fragment_home),
    SearchView.OnQueryTextListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var noteViewModel: NoteViewModel
    private lateinit var noteAdapter: NoteAdapter

    private lateinit var imageLang: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(
            inflater,
            container,
            false
        )

        imageLang = binding.imageLang

        val searchItem = binding.inputSearch as SearchView

        searchItem.isSubmitButtonEnabled = true

        searchItem.setOnQueryTextListener(this)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        noteViewModel = (activity as MainActivity).noteViewModel

        setupRecyclerView()

        // TODO binding.addNote.setOnClickListener { mView ->
        //     mView.findNavController().navigate(R.id.)
        //  TODO }

        imageLang.setOnClickListener {
            showMenuForSelectionLanguage()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupRecyclerView() {
        noteAdapter = NoteAdapter()

        binding.noteRecyclerView.apply {

            layoutManager = StaggeredGridLayoutManager(
                2,
                StaggeredGridLayoutManager.VERTICAL
            )

            setHasFixedSize(true)

            adapter = noteAdapter
        }

        activity?.let {
            noteViewModel.getAllNotes().observe(viewLifecycleOwner) { notes ->
                noteAdapter.differ.submitList(notes)

                updateUI(notes)
            }
        }
    }

    private fun showMenuForSelectionLanguage() {
        val context =  activity?.applicationContext
        val menu = PopupMenu(
            context,
            activity?.findViewById(R.id.fragment)
        )

        val id_1 = Identifiers.ID_RUSSIAN_LANGUAGE
        val id_2 = Identifiers.ID_RUSSIAN_LANGUAGE
        menu.menu.add(0, id_1, Menu.NONE, "Russian")
        menu.menu.add(0, id_2, Menu.NONE, "English")

        menu.setOnMenuItemClickListener {
            when(it.itemId) {
                id_1 -> {

                }
                id_2 -> {

                }
            }
        }
    }
    private fun updateUI(notes: List<Note>) {
        if (notes.isNotEmpty()) {
            binding.noteRecyclerView.visibility = View.VISIBLE
            binding.textNoNotes.visibility = View.GONE
        } else {
            binding.noteRecyclerView.visibility = View.GONE
            binding.textNoNotes.visibility = View.VISIBLE
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let {
            searchNotes(query)
        }
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        newText?.let {
            searchNotes(newText)
        }
        return true
    }

    private fun searchNotes(query: String?) {
        val searchQuery = "%$query%"

        noteViewModel.searchNote(searchQuery).observe(this) { list ->
            noteAdapter.differ.submitList(list)
        }
    }
}