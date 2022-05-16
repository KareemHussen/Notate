package com.example.notate.DB

import androidx.room.*

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note : NoteModel)

    @Update
    suspend fun updateNote(note : NoteModel)

    @Delete
    suspend fun deleteNote(note: NoteModel)

    @Query("select * from NotesTable where not isArchived")
    suspend fun getNotes() : MutableList<NoteModel>

    @Query("select * from NotesTable where isArchived")
    suspend fun getArchived() : MutableList<NoteModel>

    @Query("select * from NotesTable")
    fun getReminders() : MutableList<NoteModel>

    @Query("select * from NotesTable where title LIKE '%' || :searchQuery || '%' or content LIKE '%' || :searchQuery || '%'")
    suspend fun searchNotes(searchQuery: String) : MutableList<NoteModel>
}