package hr.fer.ruazosa.noteapplication

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

@Database(entities = arrayOf(Note::class), version = 1)
abstract class NotesDatabase: RoomDatabase() {
    abstract fun notesDao(): NotesDao

    companion object{
        @Volatile private var instance: NotesDatabase? = null
        private var LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also{
                instance = it
            }
        }

        private fun buildDatabase(context: Context)= Room.databaseBuilder(context,
            NotesDatabase::class.java, "notes_database").build()

    }
}