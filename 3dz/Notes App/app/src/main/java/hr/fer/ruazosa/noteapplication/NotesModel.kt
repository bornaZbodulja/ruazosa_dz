package hr.fer.ruazosa.noteapplication

import java.io.Serializable
import java.util.Date

class Note: Serializable{
    var noteTitle: String ? = null
    var noteDescription: String ? = null
    var noteDate: Date? = null
}

