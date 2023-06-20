package com.example.notesversiontwo.adapters

import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.notesversiontwo.databinding.ItemContainerNoteBinding
import com.example.notesversiontwo.model.Note

class NoteAdapter : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    private var binding: ItemContainerNoteBinding? = null

    class NoteViewHolder(val itemBinding: ItemContainerNoteBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    private val differCallback =
        object : DiffUtil.ItemCallback<Note>() {
            override fun areItemsTheSame(
                oldItem: Note,
                newItem: Note
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Note,
                newItem: Note
            ): Boolean {
                return oldItem == newItem
            }
        }

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

        val gradientColor = holder.itemBinding.layoutNote.background as GradientDrawable

        gradientColor.setColor(
            Color.parseColor(currentNote?.noteColor ?: "#333333")
        )

        currentNote.imagePath?.let {
            holder.itemBinding.imageNote
                .setImageBitmap(
                    BitmapFactory
                        .decodeFile(currentNote.imagePath)
                )
            holder.itemBinding.imageNote.visibility = View.VISIBLE
        } ?: { holder.itemBinding.imageNote.visibility = View.GONE }

        holder.itemBinding.textTitle.text = currentNote.noteTitle

        holder.itemBinding.textSubtitle.text = currentNote.noteTitle

        holder.itemView.setOnClickListener { mView ->

            val bundle = Bundle()
            bundle.putParcelable("note", currentNote)

            // TODO ПЕРЕХОД НА ЭКРАН ОБНОВЛЕНИЯ ЗАМЕТКИ
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}