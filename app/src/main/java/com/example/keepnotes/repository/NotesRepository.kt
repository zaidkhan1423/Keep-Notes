package com.example.keepnotes.repository

import androidx.lifecycle.LiveData
import com.example.keepnotes.Notes
import com.example.keepnotes.database.NotesDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NotesRepository(private val dao: NotesDao) {

    fun getAllNotes(): LiveData<List<Notes>> {
        return dao.getAllNotes()
    }
    fun getLowPriorityNotes(): LiveData<List<Notes>> {
        return dao.getLowPriorityNotes()
    }
    fun getMediumPriorityNotes(): LiveData<List<Notes>> {
        return dao.getMediumPriorityNotes()
    }
    fun getHighPriorityNotes(): LiveData<List<Notes>> {
        return dao.getHighPriorityNotes()
    }
    fun insertNotes(notes: Notes) {
        GlobalScope.launch(Dispatchers.IO) {
            dao.insertNotes(notes)
        }
    }
    fun deleteNotes(id: Int){
        GlobalScope.launch(Dispatchers.IO) {
            dao.deleteNotes(id)
        }
    }
    fun updateNotes(notes: Notes){
        GlobalScope.launch(Dispatchers.IO) {
            dao.updateNotes(notes)
        }
    }
}