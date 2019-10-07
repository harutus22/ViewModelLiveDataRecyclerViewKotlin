package com.example.viewmodellivedatarecyclerviewkotlin.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.viewmodellivedatarecyclerviewkotlin.R
import com.example.viewmodellivedatarecyclerviewkotlin.constants.NOTE_EDIT
import com.example.viewmodellivedatarecyclerviewkotlin.constants.RETURN_NOTE
import com.example.viewmodellivedatarecyclerviewkotlin.model.Note
import kotlinx.android.synthetic.main.activity_add_edit.*

class AddEditActivity : AppCompatActivity() {

    var needToEdit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit)
        createSpinner()
        getNote()
    }

    private fun createSpinner() {
        val arrayAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
            resources.getStringArray(R.array.spinnerRating))
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = arrayAdapter
    }

    private fun getNote() {
        val note = intent.getParcelableExtra<Note>(NOTE_EDIT)
        if (note != null){
            needToEdit = true
            addEditTitle.setText(note.title)
            addEditDescription.setText(note.description)
            spinner.setSelection(note.priority - 1)
        }
    }

    fun saveNote(view: View){
        if (checkFields()){
            val title = addEditTitle.text.toString()
            val description = addEditDescription.text.toString()
            val rating = spinner.selectedItem.toString().toInt()
            var id = 0
            if (needToEdit){
                 id = intent.getParcelableExtra<Note>(NOTE_EDIT)!!.id
            }
            val note = Note(id, title, description, rating)
            finishNote(note)
        }
    }

    private fun finishNote(note: Note) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(RETURN_NOTE, note)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private fun checkFields(): Boolean{
        if (addEditTitle.text.isEmpty() && addEditDescription.text.isEmpty()){
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }
}
