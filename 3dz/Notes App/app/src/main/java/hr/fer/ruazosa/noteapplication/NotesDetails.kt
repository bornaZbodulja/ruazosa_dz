package hr.fer.ruazosa.noteapplication

import android.arch.lifecycle.ViewModelProvider
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_notes_details.*
import java.util.*

class NotesDetails : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes_details)

        val viewModel =
            ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(
                NotesViewModel::class.java)

        var note = Note()
        var position: Int? = null
        if(intent.getSerializableExtra("note") != null){
            position = intent.extras.getInt("position", -1)
            note = intent.getSerializableExtra("note") as Note
            noteTitleEditText.setText(note.noteTitle)
            noteDescriptionEditText.setText(note.noteDescription)
            saveNoteButton.text = "Update Note"
        }

        saveNoteButton.setOnClickListener {
            if (position == null || position == -1){
                val newNote = Note()
                newNote.noteTitle = noteTitleEditText.text.toString()
                newNote.noteDescription = noteDescriptionEditText.text.toString()
                newNote.noteDate = Date()
                viewModel.saveNoteToRepository(newNote)
                finish()
            }else{
                note.noteTitle = noteTitleEditText.text.toString()
                note.noteDescription = noteDescriptionEditText.text.toString()
                note.noteDate = Date()
                viewModel.updateNoteFromRepository(note, position)
                finish()
            }

        }
    }


}
