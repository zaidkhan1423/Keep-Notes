package com.example.keepnotes.viewmodal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.keepnotes.repository.NotesRepository

class NotesViewModalFactory(val repository: NotesRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NotesViewModal(repository) as T
    }
}