package com.example.notes.adapters

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R
import com.example.notes.databinding.NoteLayoutAdapterBinding
import com.example.notes.model.Note
import kotlin.random.Random

class NoteAdapter : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    private var binding: NoteLayoutAdapterBinding? = null

    class NoteViewHolder(itemBinding: NoteLayoutAdapterBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    private val differCallback =
        object : DiffUtil.ItemCallback<Note>() {
            /**
             * DiffUtil.ItemCallback - класс,
             * ответственный за вычисление разницы между двумя списками
             */
            override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
                /** сравниваем индексы элементов */
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
                /** сравниваем содержимое элементов */
                return oldItem == newItem
            }
        }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NoteViewHolder {

        binding = NoteLayoutAdapterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return NoteViewHolder(binding!!)
    }

    override fun onBindViewHolder(
        holder: NoteViewHolder,
        position: Int
    ) {
        val currentNote = differ.currentList[position]

        holder.itemView.apply {

            binding?.tvNoteTitle?.text = currentNote.noteTitle
            binding?.tvNoteBody?.text = currentNote.noteBody

            val random = Random()

            val color = Color.argb(
                255,
                random.nextInt(256),
                random.nextInt(256),
                random.nextInt(256)
            )

            binding?.viewColor?.setBackgroundColor(color)

        }.setOnClickListener { mView ->

            val bundle = Bundle()
            bundle.putString("noteTitle", currentNote.noteTitle)

            mView.findNavController().navigate(
                R.id.action_homeFragment_to_updateNoteFragment,
                bundle
            )
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}