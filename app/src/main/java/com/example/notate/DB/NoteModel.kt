package com.example.notate.DB

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "NotesTable")
@Parcelize
class NoteModel(var title : String, var content : String, var isArchived : Boolean = false, var isPinned : Boolean = false, var lastEdited : String, var hasReminder : Boolean = false, var update : Boolean = false) : Parcelable {

    @PrimaryKey(autoGenerate = true )
    var id = 0

}