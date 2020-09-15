package hr.fer.ruazosa.noteapplication

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    final val REQUST_CODE = 1

    lateinit var notesAdapter: NotesAdapter
    lateinit var viewModel: NotesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listOfNotesView.layoutManager = LinearLayoutManager(applicationContext)

        val decorator = DividerItemDecoration(applicationContext, LinearLayoutManager.VERTICAL)
        decorator.setDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.cell_divider)!!)
        listOfNotesView.addItemDecoration(decorator)

        viewModel =
            ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(
                NotesViewModel::class.java)

        notesAdapter = NotesAdapter(viewModel)
        listOfNotesView.adapter = notesAdapter

        viewModel.notesList.observe(this, Observer {
            notesAdapter.notifyDataSetChanged()
        })

        newNoteActionButton.setOnClickListener {
            val intent = Intent(this, NotesDetails::class.java)
            startActivity(intent)
        }
    }


    override fun onResume() {
        super.onResume()
        viewModel.getNotesRepository()
        notesAdapter.notifyDataSetChanged()
    }

}
