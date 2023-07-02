package com.example.notesversiontwo.adapters

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.notes.R
import com.example.notes.databinding.ItemContainerNoteBinding
import com.example.notesversiontwo.model.Note

class NoteAdapter
constructor(private val context: Context) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    class NoteViewHolder(val itemBinding: ItemContainerNoteBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    /**
        * DiffUtil.ItemCallback - класс,
        * ответственный за вычисление разницы между двумя списками
     */
    private val differCallback =
        object : DiffUtil.ItemCallback<Note>() {
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

    /**
        * AsyncListDiffer - это класс,
        * который поддерживает список
        * с автоматическим обновлением
        * после изменения
     */
    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NoteViewHolder {
        return NoteViewHolder(
            ItemContainerNoteBinding.inflate(
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

        holder.itemBinding.textTitle.text = currentNote.noteTitle
        holder.itemBinding.dateTime.text = currentNote.dateTime

        val gradientColor = holder.itemBinding.layoutNote
            .background as GradientDrawable

        gradientColor.setColor(
            Color.parseColor(
                currentNote?.noteColor ?: "#333333"
            )
        )

        currentNote.imagePath?.let {
            Glide.with(context).load(it).into(holder.itemBinding.imageNote)
            holder.itemBinding.imageNote.visibility = View.VISIBLE
        } ?: { holder.itemBinding.imageNote.visibility = View.GONE }

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