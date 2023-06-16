package com.example.notes.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "notes")
@Parcelize
/**
 * Данная аннотация спользуется для
 * автоматического создания Parcelable класса
 */
data class Note(
    /**
     * data - класс содержит в себе следующие функции:
     * equals();
     * hashCode();
     * toString();
     * component() (функции, соответствующие свойствам - get() и set())
     */
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val noteTitle: String,
    val noteBody: String
) : Parcelable