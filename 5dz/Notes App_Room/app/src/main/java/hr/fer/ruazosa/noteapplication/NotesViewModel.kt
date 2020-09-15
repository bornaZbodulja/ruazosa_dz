package hr.fer.ruazosa.noteapplication

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context

class NotesViewModel: ViewModel() {
    var notesList = MutableLiveData<List<Note>>()


    fun getNotesFromDb(context: Context){
        Thread(Runnable {
            val notesDao = NotesDatabase(context = context).notesDao()
            notesList.postValue(notesDao.getAllNotes())
        }).start()
    }

    fun saveNoteToDb(note: Note, context: Context){
        Thread(Runnable {
            val notesDao = NotesDatabase(context).notesDao()
            notesDao.insertNote(note)
            notesList.postValue(notesDao.getAllNotes())
        }).start()
    }

    fun updateNoteFromDb(note: Note, context: Context){
        Thread(Runnable {
            val notesDao = NotesDatabase(context).notesDao()
            notesDao.updateNote(note)
            notesList.postValue(notesDao.getAllNotes())
        }).start()
    }

    fun deleteNoteFromDb(note: Note, context: Context){
        Thread(Runnable {
            val notesDao = NotesDatabase(context).notesDao()
            notesDao.deleteNote(note)
            notesList.postValue(notesDao.getAllNotes())
        }).start()
    }
}