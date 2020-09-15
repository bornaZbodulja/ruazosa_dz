package hr.fer.ruazosa.noteapplication
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class NotesAdapter(listOfNotesViewModel: NotesViewModel, context: Context): RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    var listOfNotes: NotesViewModel = listOfNotesViewModel
    val context = context

    class ViewHolder(itemView: View, context: Context, listOfNotes: NotesViewModel): RecyclerView.ViewHolder(itemView) {
        var noteTitleTextView: TextView? = null
        var noteDateTextView: TextView? = null
        var deleteButton: Button? = null
        val context = context
        init {
            noteTitleTextView = itemView.findViewById(R.id.noteTitleTextView)
            noteDateTextView = itemView.findViewById(R.id.noteDateTextView)
            deleteButton = itemView.findViewById(R.id.deleteButton)

            itemView.setOnClickListener {
                val position = adapterPosition
                val intent = Intent(context, NotesDetails::class.java)
                val bundle: Bundle? = Bundle()
                bundle!!.putSerializable("note", listOfNotes.notesList.value!![position])
                intent.putExtras(bundle)
                context.startActivity(intent)
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): NotesAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val notesListElement = inflater.inflate(R.layout.note_list_element, parent, false)
        return ViewHolder(notesListElement, context, listOfNotes)
    }

    override fun getItemCount(): Int {
        if (listOfNotes.notesList.value != null) {
            return listOfNotes.notesList.value!!.count()
        }
        return 0
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        if (listOfNotes != null) {
            viewHolder.noteTitleTextView?.text = listOfNotes.notesList.value!![position].noteTitle

            viewHolder.noteDateTextView?.text =
                listOfNotes.notesList.value!![position].noteDate

            viewHolder.deleteButton?.setOnClickListener{

                listOfNotes.deleteNoteFromDb(listOfNotes.notesList.value!![position], context)
                notifyDataSetChanged()
            }


        }
    }



}