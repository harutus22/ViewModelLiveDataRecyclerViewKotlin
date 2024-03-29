package com.example.viewmodellivedatarecyclerviewkotlin.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.viewmodellivedatarecyclerviewkotlin.R
import com.example.viewmodellivedatarecyclerviewkotlin.constants.*
import com.example.viewmodellivedatarecyclerviewkotlin.model.Note
import com.example.viewmodellivedatarecyclerviewkotlin.recyclerview.NoteAdapter
import com.example.viewmodellivedatarecyclerviewkotlin.recyclerview.OnNoteItemClickListener
import com.example.viewmodellivedatarecyclerviewkotlin.viewmodel.NoteViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), OnNoteItemClickListener {
    val noteAdapter = NoteAdapter()
    val noteViewModel: NoteViewModel by lazy {
        ViewModelProviders.of(this).get(NoteViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createViewModel()
        createRecyclerView(this)
        createItemTouchHelper()
    }

    private fun createItemTouchHelper() {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(1, ItemTouchHelper.LEFT.or
            (ItemTouchHelper.RIGHT)){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                when(direction){
                    ItemTouchHelper.LEFT -> {
                        val note = noteAdapter.noteAt(viewHolder.adapterPosition)
                        noteAdapter.removedNote = note
                        val snackBar = Snackbar.make(floatingActionButton, "Item deleted", Snackbar.LENGTH_LONG)
                        snackBar.setAction("Undo") {
                            noteViewModel.addNote(note)
                            Snackbar.make(floatingActionButton, "Item is restored!", Snackbar.LENGTH_SHORT).show()
                        }
                        noteViewModel.deleteNote(note)
                        snackBar.show()
                    }
                    ItemTouchHelper.RIGHT -> moveToDetailActivity(request = REQUEST_ADD)
                }
            }
        }).attachToRecyclerView(noteRecyclerView)
    }

    private fun createViewModel() {
        noteViewModel.noteLiveData.observe(this, Observer {
            noteAdapter.submitList(it)
        })
    }

    private fun createRecyclerView(context: Context) {
        noteAdapter.onNoteItemClickListener = this
        noteRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = noteAdapter
        }
    }

    override fun onNoteClicked(note: Note) {
        moveToDetailActivity(note, NOTE_EDIT, REQUEST_EDIT)
    }

    private fun moveToDetailActivity(note: Note? = null, mission: String? = null, request: Int = REQUEST_ADD) {
        val intent = Intent(this, AddEditActivity::class.java)
        intent.putExtra(mission, note)
        startActivityForResult(intent, request)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val note = data!!.getParcelableExtra<Note>(RETURN_NOTE)
        if (requestCode == REQUEST_EDIT){
            if (resultCode == Activity.RESULT_OK){
                noteViewModel.updateNote(note)
            }
        } else if(requestCode == REQUEST_ADD){
            if (resultCode == Activity.RESULT_OK){
                noteViewModel.addNote(note)
                noteAdapter.notifyDataSetChanged()
            }
        }
    }

    fun addNote(view: View){
        moveToDetailActivity(request = REQUEST_ADD)
    }
}
