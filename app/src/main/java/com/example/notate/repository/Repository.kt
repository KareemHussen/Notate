package com.example.notate.repository

import android.content.Context
import androidx.lifecycle.viewModelScope
import androidx.room.Query
import com.example.notate.DB.NoteDatabase
import com.example.notate.DB.NoteModel
import kotlinx.coroutines.launch


class Repository {


    suspend fun getNotes(context: Context) = NoteDatabase.instanceOfDatabase(context).noteDao().getNotes()

    suspend fun getArchivedData(context: Context) = NoteDatabase.instanceOfDatabase(context).noteDao().getArchived()

    suspend fun insertNote(noteModel: NoteModel, context: Context) = NoteDatabase.instanceOfDatabase(context).noteDao().insertNote(noteModel)

    suspend fun updateNote(noteModel: NoteModel, context: Context) = NoteDatabase.instanceOfDatabase(context).noteDao().updateNote(noteModel)

    suspend fun deleteNote(noteModel: NoteModel, context: Context) = NoteDatabase.instanceOfDatabase(context).noteDao().deleteNote(noteModel)

    suspend fun searchNote(query: String, context: Context) = NoteDatabase.instanceOfDatabase(context).noteDao().searchNotes(query)
}