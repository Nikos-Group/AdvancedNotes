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

class NoteAdapter : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    private var binding: NoteLayoutAdapterBinding? = null

    class NoteViewHolder(val itemBinding: NoteLayoutAdapterBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    /** используется для сравнения двух списков типа Note */
    private val differCallback =
        object : DiffUtil.ItemCallback<Note>() {
            /**
             * DiffUtil.ItemCallback - класс,
             * ответственный за вычисление разницы между двумя списками
             */
            override fun areItemsTheSame(
                oldItem: Note,
                newItem: Note
            ): Boolean {
                /** сравниваем идентификаторы элементов */
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Note,
                newItem: Note
            ): Boolean {
                /** сравниваем содержимое элементов */
                return oldItem == newItem
            }
        }

    val differ = AsyncListDiffer(this, differCallback)

    /**
     * AsyncListDiffer - это класс,
     * который поддерживает список с автоматическим обновлением
     * после изменения
     */

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NoteViewHolder {
        return NoteViewHolder(
            NoteLayoutAdapterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: NoteViewHolder,
        position: Int
    ) {
        val currentNote = differ.currentList[position]

        holder.itemBinding.tvNoteTitle.text = currentNote.noteTitle

        if (currentNote.noteBody.length >= 60) {
            holder.itemBinding.tvNoteBody.text = currentNote.noteBody.take(60) + "..."
        } else {
            holder.itemBinding.tvNoteBody.text = currentNote.noteBody
        }

        val random = java.util.Random()

        val color = Color.argb(
            255,
            random.nextInt(256),
            random.nextInt(256),
            random.nextInt(256)
        )

        holder.itemBinding.viewColor.setBackgroundColor(color)

        holder.itemView.setOnClickListener { mView ->

            val bundle = Bundle()
            bundle.putParcelable("note", currentNote)

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