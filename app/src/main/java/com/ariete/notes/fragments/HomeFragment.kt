package com.ariete.notes.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ariete.notes.R
import com.ariete.notes.activities.MainActivity
import com.ariete.notes.adapters.NoteAdapter
import com.ariete.notes.databinding.FragmentHomeBinding
import com.ariete.notes.model.Note
import com.ariete.notes.viewmodel.NoteViewModel

class HomeFragment : Fragment(R.layout.fragment_home), SearchView.OnQueryTextListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var noteViewModel: NoteViewModel
    private lateinit var noteAdapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(
            inflater,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        noteViewModel = (activity as MainActivity).noteViewModel

        setupRecyclerView()

        binding.fabAddNote.setOnClickListener { mView ->
            mView.findNavController().navigate(
                R.id.action_homeFragment_to_newNoteFragment
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupRecyclerView() {
        noteAdapter = NoteAdapter()

        binding.recyclerView.apply {

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
                /**
                    * The submitList() passed a new List to the AdapterHelper.
                    * Adapter updates will be computed on a background thread.
                    *
                    * If a List is already present,
                    * a diff also will be computed asynchronously.
                    *
                    * When the difference is calculated, the list changes.
                    * --------------------------------------------------------------
                    * Функция submitList() передает новый список AdapterHelper - у.
                    * Обновления адаптера "будут вычисляться" в фоновом потоке.
                    *
                    * Если список уже существует,
                    * разница также будет вычисляться асинхронно.
                    *
                    * Когда разница вычислена, список изменяется.
                */
                updateUI(notes)
            }
        }
    }

    private fun updateUI(notes: List<Note>) {
        if (notes.isNotEmpty()) {
            binding.recyclerView.visibility = View.VISIBLE
            binding.tvNoNotesAvailable.visibility = View.GONE
        } else {
            binding.recyclerView.visibility = View.GONE
            binding.tvNoNotesAvailable.visibility = View.VISIBLE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        menu.clear()
        inflater.inflate(R.menu.home_menu, menu)

        val searchItem = menu.findItem(R.id.menu_search)

        val mMenuSearch = searchItem.actionView as SearchView

        mMenuSearch.isSubmitButtonEnabled = true
        /**
            * We set this property to true for
            * display submit button of query.
            * -------------------------------
            * Мы устанавливаем в это свойство значение true для
            * отображения кнопки отправки запроса.
        */

        mMenuSearch.setOnQueryTextListener(this)
        /**
            * The function setOnQueryTextListener() is needed to
            * setting a listener for user actions within the SearchView.
            * ----------------------------------------------------------
            * Функция setOnQueryTextListener() необходима для
            * настройки "прослушивателя действий" пользователя в SearchView.
        */
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        /**
            * This function сalled when the user submits the query.
            * -----------------------------------------------------
            * Эта функция вызывается, когда пользователь отправляет запрос.
        */

        query?.let {
            searchNotes(query)
        }

        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        /**
            * This function called when the query text is changed by the user.
            * ----------------------------------------------------------------
            * Эта функция вызывается, когда пользователь изменяет текст запроса.
        */

        newText?.let {
            searchNotes(newText)
        }
        return true
    }

    private fun searchNotes(query: String?) {
        val searchQuery = "%$query%"

        noteViewModel.searchNotes(searchQuery).observe(this) { list ->
            noteAdapter.differ.submitList(list)
        }
    }
}