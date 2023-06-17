package com.example.notes.model

import android.os.Parcelable
import androidx.room.ColumnInfo
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
    @ColumnInfo(name = "noteTitle")
    val noteTitle: String,
    @ColumnInfo(name = "noteBody")
    val noteBody: String
) : Parcelable