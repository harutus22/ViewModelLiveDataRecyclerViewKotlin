package com.example.viewmodellivedatarecyclerviewkotlin.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.viewmodellivedatarecyclerviewkotlin.R
import com.example.viewmodellivedatarecyclerviewkotlin.model.Note

class NoteAdapter: ListAdapter<Note, NoteAdapter.NoteViewHolder>(DIFFCALLBACK()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item_view,
            parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    class NoteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(note: Note){

        }
    }

    companion object{
        class DIFFCALLBACK: DiffUtil.ItemCallback<Note>(){
            override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem.title == newItem.title &&
                        oldItem.description == newItem.description &&
                        oldItem.priority == newItem.priority
            }

        }
    }
}