package com.example.viewmodellivedatarecyclerviewkotlin.repository

import android.app.Application
import com.example.viewmodellivedatarecyclerviewkotlin.constants.*
import com.example.viewmodellivedatarecyclerviewkotlin.model.Note
import com.example.viewmodellivedatarecyclerviewkotlin.room.NoteDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NoteRepository(application: Application){
    private val noteDatabase = NoteDatabase.invoke(application)
    private val noteDao = noteDatabase.noteDao
    val notesLiveData = noteDao.getAllNotes()

    fun insert(note: Note){
        makeAction(note, ADD)
    }

    fun update(note: Note){
        makeAction(note, UPDATE)
    }

    fun delete(note: Note){
        makeAction(note, DELETE)
    }

    fun deleteAll(){
        makeAction(text = DELETE_ALL)
    }

    private fun makeAction(note: Note? = null, text: String) {
        GlobalScope.launch { when(text){
            ADD -> noteDao.addNote(note)
            UPDATE -> noteDao.updateNote(note)
            DELETE -> noteDao.deleteNote(note)
            DELETE_ALL -> noteDao.deleteAllNotes()
        } }
    }
}