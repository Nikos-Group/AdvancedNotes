package com.nikos_group.advancednotes.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "notes")
@Parcelize
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "noteTitle")
    var noteTitle: String,

    @ColumnInfo(name = "dateTime")
    var dateTime: String,

    @ColumnInfo(name = "noteBody")
    var noteBody: String,

    @ColumnInfo(name = "imagePath")
    var imagePath: String? = null,

    @ColumnInfo(name = "noteColor")
    var noteColor: String? = null,

    @ColumnInfo(name = "webLink")
    var webLink: String? = null,
) : Parcelable