package com.ariete.notes.adapters

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ariete.notes.R
import com.ariete.notes.databinding.NoteLayoutAdapterBinding
import com.ariete.notes.model.Note

class NoteAdapter : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    class NoteViewHolder(val itemBinding: NoteLayoutAdapterBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    /**
        * DiffUtil.ItemCallback - class,
        * responsible for calculating the difference between two lists.
        * -------------------------------------------------------------
        * DiffUtil.ItemCallback - класс,
        * ответственный за вычисление разницы между двумя списками.
    */
    private val differCallback =
        object : DiffUtil.ItemCallback<Note>() {
            override fun areItemsTheSame(
                oldItem: Note,
                newItem: Note
            ): Boolean {
                /**
                    * We compare identificators of elements.
                    * -------------------------------------
                    * Сравниваем идентификаторы элементов.
                */
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Note,
                newItem: Note
            ): Boolean {
                /**
                    * We compare contents of elements.
                    * ---------------------------------
                    * Сравниваем содержимое элементов.
                */
                return oldItem == newItem
            }
        }

    /**
        * AsyncListDiffer - it is a class,
        * which support a list
        * with auto updating
        * after changing.
        * ---------------------------
        * AsyncListDiffer - это класс,
        * который поддерживает список
        * с автоматическим обновлением
        * после изменения.
    */
    val differ = AsyncListDiffer(
        this,
        differCallback
    )

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

        currentNote.noteBody.let {
            if (it.length <= 15) {
                holder.itemBinding.tvNoteBody.text = it
            } else {
                holder.itemBinding.tvNoteBody.text = "${it.substring(0, 15)}..."
            }
        }

        val random = java.util.Random()

        /**
            * Color.argb(...) - this is a function
            * that creates a color using the Android Color class.
            * ---------------------------------------------------
            * Color.argb(...) - это функция, которая создает
            * цвет, используя класс Color.
        */
        val color = Color.argb(
            /**
                * The first argument (alpha) is set to 255,
                * which means the color is fully opaque.rency
                * -------------------------------------------
                * Первый аргумент (alpha) имеет значение 255.
                * Это значит что цвет полностью непрозрачный
            */
            255,
            random.nextInt(256), // red component
            random.nextInt(256), // green component
            random.nextInt(256) // blue component
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