package hr.fer.ruazosa.noteapplication

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable
import java.util.Date

@Entity(tableName = "note_table")
class Note: Serializable{
    @PrimaryKey(autoGenerate = true)
    var uid: Int? = null
    @ColumnInfo(name = "note_title")
    var noteTitle: String ? = null
    @ColumnInfo(name = "note_description")
    var noteDescription: String ? = null
    @ColumnInfo(name = "note_date")
    var noteDate: String? = null
}

