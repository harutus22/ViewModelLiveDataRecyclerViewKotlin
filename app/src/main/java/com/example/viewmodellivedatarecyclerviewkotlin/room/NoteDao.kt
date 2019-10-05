package com.example.viewmodellivedatarecyclerviewkotlin.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.viewmodellivedatarecyclerviewkotlin.model.Note

@Dao
interface NoteDao{
    @Insert
    fun addNote(note: Note?)

    @Update
    fun updateNote(note: Note?)

    @Delete
    fun deleteNote(note: Note?)

    @Query("SELECT * FROM note_table ORDER BY priority_column DESC")
    fun getAllNotes(): LiveData<List<Note>>

    @Query("DELETE FROM note_table")
    fun deleteAllNotes()
}