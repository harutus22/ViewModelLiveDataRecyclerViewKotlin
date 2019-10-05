package com.example.viewmodellivedatarecyclerviewkotlin.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.viewmodellivedatarecyclerviewkotlin.model.Note
import com.example.viewmodellivedatarecyclerviewkotlin.repository.NoteRepository

class NoteViewModel(application: Application): ViewModel() {
    private val repository = NoteRepository(application)
    val noteLiveData = repository.notesLiveData

    fun addNote(note: Note){
        repository.insert(note)
    }

    fun updateNote(note: Note){
        repository.update(note)
    }

    fun deleteNote(note: Note){
        repository.delete(note)
    }

    fun deleteAll(){
        repository.deleteAll()
    }

}