package com.example.keepnotes

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "Notes")
@Parcelize
data class Notes(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val title: String,
    val notes: String,
    val priority: String,
    val date: String
): Parcelable