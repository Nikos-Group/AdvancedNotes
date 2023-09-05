package com.ariete.notes.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "notes")

/**
    * The @Parcelize annotation is needed to automatically create the Parcelable class.
    * ---------------------------------------------------------------------------------
    * Аннотация @Parcelize нужна для автоматического создания Parcelable класса.
*/
@Parcelize
data class Note(
    /**
        * Data class automatically creates the following functions:
        * equals() and hashCode(),
        * toString(),
        * component() functions for each of the parameters,
        * copy().
        * ---------------------------------------------------------
        * Data class автоматически создает следующие функции:
        * equals() and hashCode(),
        * toString(),
        * component() функции для каждого из параметров,
        * copy().
    */
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "noteTitle")
    val noteTitle: String,
    @ColumnInfo(name = "noteBody")
    val noteBody: String,
) : Parcelable