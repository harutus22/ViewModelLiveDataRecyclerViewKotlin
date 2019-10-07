package com.example.viewmodellivedatarecyclerviewkotlin.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.viewmodellivedatarecyclerviewkotlin.R
import com.example.viewmodellivedatarecyclerviewkotlin.model.Note
import kotlinx.android.synthetic.main.note_item_view.view.*

class NoteAdapter:
    ListAdapter<Note, NoteAdapter.NoteViewHolder>(DIFFCALLBACK()) {
    var onNoteItemClickListener: OnNoteItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item_view,
            parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = getItem(position)
        holder.bind(note, onNoteItemClickListener)
    }

    class NoteViewHolder(itemView: View):
        RecyclerView.ViewHolder(itemView){
        fun bind(note: Note, onNoteItemClickListener: OnNoteItemClickListener?){
            itemView.notePriority.text = note.priority.toString()
            itemView.noteTitle.text = note.title
            itemView.noteDescription.text = note.description

            if(onNoteItemClickListener != null) {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION){
                    itemView.setOnClickListener {
                        onNoteItemClickListener.onNoteClicked(note)
                    }
                }
            }
        }
    }

    fun noteAt(position: Int) = getItem(position)

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

interface OnNoteItemClickListener{
    fun onNoteClicked(note: Note)
}