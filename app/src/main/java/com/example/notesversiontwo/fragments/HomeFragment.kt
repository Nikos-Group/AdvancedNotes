package com.example.notesversiontwo.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView;
import androidx.navigation.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notes.R
import com.example.notes.databinding.FragmentHomeBinding
import com.example.notesversiontwo.activities.MainActivity
import com.example.notesversiontwo.adapters.NoteAdapter
import com.example.notesversiontwo.model.Note
import com.example.notesversiontwo.viewmodel.NoteViewModel

class HomeFragment : Fragment(R.layout.fragment_home),
    SearchView.OnQueryTextListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var noteViewModel: NoteViewModel
    private lateinit var noteAdapter: NoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(
            inflater,
            container,
            false
        )

        setting_up_searchItem()

        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        noteViewModel = (activity as MainActivity).noteViewModel

        setupRecyclerView()

        binding.addNote.setOnClickListener { mView ->
            mView.findNavController().navigate(
                R.id.action_homeFragment_to_createNoteFragment
            )
        }
        /**
            * Когда пользователь нажимает на изображение,
            * оно передает экземпляр текущего представления мView
            * в функцию обратного вызова
            *
            * Функция findNavController() вызывается у mView
            * для поиска объекта NavController,
            * который управляет навигацией между фрагментами
            *
            * Затем используется функция navigate(),
            * который принимает идентификатор действия перехода
            * из текущего фрагмента
            * в фрагмент CreateNoteFragment
         */
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupRecyclerView() {
        noteAdapter = activity?.let { NoteAdapter(it.baseContext) }!!

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

    private fun updateUI(notes: List<Note>) {
        if (notes.isNotEmpty()) {
            binding.noteRecyclerView.visibility = View.VISIBLE
            binding.textNoNotes.visibility = View.GONE
        } else {
            binding.noteRecyclerView.visibility = View.GONE
            binding.textNoNotes.visibility = View.VISIBLE
        }
    }

    private fun `setting_up_searchItem`() {
        val searchItem = binding.inputSearch

        searchItem.isSubmitButtonEnabled = true
        searchItem.setOnQueryTextListener(this)
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