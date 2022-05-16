package com.example.notate.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Query
import com.example.notate.DB.NoteDatabase
import com.example.notate.DB.NoteModel
import com.example.notate.repository.Repository
import kotlinx.coroutines.launch

class NoteViewModel(private var repository: Repository , private var context : Context) : ViewModel() {

    val notesMutable : MutableLiveData<MutableList<NoteModel>> = MutableLiveData()

    val archivedNotesMutable : MutableLiveData<MutableList<NoteModel>> = MutableLiveData()

    val searchQueryMutable : MutableLiveData<MutableList<NoteModel>> = MutableLiveData()

    fun getNotes(){
        viewModelScope.launch {
            notesMutable.value = repository.getNotes(context)
        }
    }


     fun getArchivedData() {
         viewModelScope.launch {
             archivedNotesMutable.value = repository.getArchivedData(context)
         }

     }

     fun insertNote(noteModel: NoteModel) {
         viewModelScope.launch {
             repository.insertNote(noteModel , context)
         }
     }

     fun updateNote(noteModel: NoteModel)  {
         viewModelScope.launch {
             repository.updateNote(noteModel , context)
         }

     }

     fun deleteNote(noteModel: NoteModel) {
         viewModelScope.launch {
             repository.deleteNote(noteModel , context)
         }

     }

    fun searchNote(query: String) {
        viewModelScope.launch {
            Log.d("ahhh" , repository.searchNote(query , context).toString())
            searchQueryMutable.value = repository.searchNote(query , context)
        }

    }




}