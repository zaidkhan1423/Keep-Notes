package com.example.keepnotes.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.keepnotes.Notes

@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotes(notes: Notes)

    @Update
    suspend fun updateNotes(notes: Notes)

    @Query("DELETE FROM Notes WHERE id=:id")
    suspend fun deleteNotes(id: Int)

    @Query("SELECT * FROM NOTES")
    fun getAllNotes(): LiveData<List<Notes>>

    @Query("SELECT * FROM Notes WHERE priority = 1")
    fun getLowPriorityNotes(): LiveData<List<Notes>>

    @Query("SELECT * FROM Notes WHERE priority = 2")
    fun getMediumPriorityNotes(): LiveData<List<Notes>>

    @Query("SELECT * FROM Notes WHERE priority = 3")
    fun getHighPriorityNotes(): LiveData<List<Notes>>

}