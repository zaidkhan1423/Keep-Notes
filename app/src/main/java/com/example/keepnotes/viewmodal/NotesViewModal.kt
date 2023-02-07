package com.example.keepnotes.viewmodal

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.keepnotes.Notes
import com.example.keepnotes.repository.NotesRepository

class NotesViewModal(private val repository: NotesRepository): ViewModel() {

    fun getAllNotes(): LiveData<List<Notes>> {
        return repository.getAllNotes()
    }
    fun getLowNotes(): LiveData<List<Notes>> {
        return repository.getLowPriorityNotes()
    }
    fun getMediumNotes(): LiveData<List<Notes>> {
        return repository.getMediumPriorityNotes()
    }
    fun getHighNotes(): LiveData<List<Notes>> {
        return repository.getHighPriorityNotes()
    }
    fun insertNotes(notes: Notes){
        repository.insertNotes(notes)
    }
    fun deleteNotes(id: Int){
        repository.deleteNotes(id)
    }
    fun updateNotes(notes: Notes){
        repository.updateNotes(notes)
    }

}