package hr.fer.ruazosa.noteapplication

import android.arch.persistence.room.*

@Dao
interface NotesDao {
    @Query("SELECT * FROM note_table")
    fun getAllNotes(): List<Note>

    @Insert
    fun insertNote(vararg notes: Note)

    @Update
    fun updateNote(note: Note)

    @Delete
    fun deleteNote(note: Note)
}