package com.example.viewmodellivedatarecyclerviewkotlin.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.viewmodellivedatarecyclerviewkotlin.model.Note

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase: RoomDatabase(){

    abstract val noteDao: NoteDao

    companion object{
        @Volatile private var instance: NoteDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also{ instance = it}
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
            NoteDatabase::class.java, "note_database").fallbackToDestructiveMigration()
            .build()
    }


}