package hr.fer.ruazosa.lectures

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.widget.*
import hr.fer.tel.ruazosa.lectures.entity.ShortCourse
import hr.fer.tel.ruazosa.lectures.entity.ShortPerson
import hr.fer.tel.ruazosa.lectures.net.RestFactory
import kotlinx.android.synthetic.main.delete_person_pop_up.view.*

class MainActivity : AppCompatActivity() {

    private var listView: ListView? = null
    private var loadCourseButton: Button? = null
    private var loadPersonsButton: Button? = null
    private var loadCourseBool = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.listView) as ListView
        listView?.onItemClickListener = AdapterView.OnItemClickListener { parent, _, position, _ ->
            if (loadCourseBool == false){

                val itemPosition = parent.getItemAtPosition(position)
                val shortPerson = itemPosition as ShortPerson
                val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
                val view = LayoutInflater.from(this).inflate(R.layout.delete_person_pop_up, null)

                dialogBuilder.setView(view)
                dialogBuilder.setCancelable(true)
                val alertDialog = dialogBuilder.create()

                view.noButton.setOnClickListener {
                    alertDialog.dismiss()
                }
                view.yesButton.setOnClickListener {
                    val deletePer = DeletePerson()
                    deletePer.execute(shortPerson.id)
                    alertDialog.dismiss()
                    Toast.makeText(this, "Person deleted", Toast.LENGTH_SHORT).show()

                }

                //val alertDialog = dialogBuilder.create()
                alertDialog.show()
            }else {

                val itemAtPosition = parent.getItemAtPosition(position)
                val shortCourse = itemAtPosition as ShortCourse
                val intent = Intent(this@MainActivity, CourseDetailsActivity::class.java)
                intent.putExtra("course", shortCourse)
                startActivity(intent)
            }
        }

        loadCourseButton = findViewById(R.id.loadCourseButton) as Button
        loadCourseButton?.setOnClickListener {
            loadCourseBool = true
            LoadCoursesTask().execute()
        }

        loadPersonsButton = findViewById(R.id.loadStudents) as Button
        loadPersonsButton?.setOnClickListener {
            LoadStudentsTask().execute()
        }
    }

    private inner class LoadCoursesTask: AsyncTask<Void, Void, List<ShortCourse>?>() {
        override fun doInBackground(vararg params: Void): List<ShortCourse>? {
            val rest = RestFactory.instance
            loadCourseBool = true
            return rest.getListOfCourses()
        }

        override fun onPostExecute(courses: List<ShortCourse>?) {
            updateCourseList(courses)
        }
    }

    private fun updateCourseList(courses: List<ShortCourse>?) {
        if(courses != null) {
            val adapter = CourseAdapter(this,
                android.R.layout.simple_list_item_1, courses)
            listView?.adapter = adapter
        } else {
            // TODO show that courses can not be loaded
        }
    }

    private inner class CourseAdapter(context: Context, textViewResourceId: Int, private val shortCourseList: List<ShortCourse>) : ArrayAdapter<ShortCourse>(context, textViewResourceId, shortCourseList)
    private inner class PersonsAdapter(context: Context, textViewResourceId: Int, private val shortPersonsList: List<ShortPerson>) : ArrayAdapter<ShortPerson>(context, textViewResourceId, shortPersonsList)

    private inner class LoadStudentsTask: AsyncTask<Void, Void, List<ShortPerson>?>(){
        override fun onPostExecute(result: List<ShortPerson>?) {
            updatePersonsList(result)
        }

        override fun doInBackground(vararg p0: Void?): List<ShortPerson>? {
            val rest = RestFactory.instance
            loadCourseBool = false
            return rest.getListOfPersons()
        }
    }

    private fun updatePersonsList(persons: List<ShortPerson>?){
        if(persons != null){
            val adapter = PersonsAdapter(this,
                android.R.layout.simple_list_item_1, persons)
            listView?.adapter = adapter
        }
    }

    private inner class DeletePerson: AsyncTask<Long?, Void, List<ShortPerson>?>(){
        override fun onPostExecute(result: List<ShortPerson>?) {
            updatePersonsList(result)
        }

        override fun doInBackground(vararg p0: Long?): List<ShortPerson>? {
            val rest = RestFactory.instance
            for (i in 1..4){
                val courseStudents = rest.getCourseStudents(i.toLong())
                courseStudents?.forEach {
                    if(it.id == p0[0]){
                        rest.disenrollPersonFromCourse(i.toLong(), p0[0])
                    }
                }
            }
            rest.deletePerson(p0[0])
            return rest.getListOfPersons()
        }
    }
}
