package hr.fer.ruazosa.lectures.net

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import hr.fer.ruazosa.lectures.R
import hr.fer.tel.ruazosa.lectures.entity.ShortPerson
import hr.fer.tel.ruazosa.lectures.net.RestFactory

class StudentsListActivity : AppCompatActivity() {

    private var studentListView: ListView? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var studentsAdapter: hr.fer.ruazosa.lectures.net.StudentsAdapter
    private var studentsList: List<ShortPerson>? = null
    private var course_id: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_students_list)

        //studentListView = findViewById(R.id.studentsListView)
        course_id = intent.getLongExtra("id", 0)
        Log.d("ID_TAG", course_id.toString())
        LoadStudentsTask().execute(course_id?.toInt())

        studentsAdapter = hr.fer.ruazosa.lectures.net.StudentsAdapter(studentsList, course_id, applicationContext)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = studentsAdapter
            addItemDecoration(
                DividerItemDecoration(this@StudentsListActivity, LinearLayoutManager.VERTICAL)

            )
        }


    }
    private inner class LoadStudentsTask: AsyncTask<Int, Void, List<ShortPerson>>(){
        override fun onPostExecute(result: List<ShortPerson>?) {
            //updatestudentsList(result)
            studentsList = result
            val adapter = StudentsAdapter(studentsList, course_id, applicationContext)
            recyclerView.adapter = adapter
            studentsList?.forEach {
                Log.d("Person", it.toString())
            }
        }

        override fun doInBackground(vararg p0: Int?): List<ShortPerson>? {
            val rest = RestFactory.instance
            return rest.getCourseStudents(p0[0]?.toLong())
        }
    }

    private fun updatestudentsList(students: List<ShortPerson>?){
        if(students != null){
            val adapter = StudentsAdapter(this,
                android.R.layout.simple_list_item_1, students)
            studentListView?.adapter = adapter
        }
    }

    private inner class StudentsAdapter(context: Context, textViewResourceId: Int, private val
    shortStudentsList: List<ShortPerson>) : ArrayAdapter<ShortPerson>(context, textViewResourceId, shortStudentsList)

}
