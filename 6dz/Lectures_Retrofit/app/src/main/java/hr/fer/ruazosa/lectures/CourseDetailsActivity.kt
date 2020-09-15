package hr.fer.ruazosa.lectures

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.widget.*
import hr.fer.ruazosa.lectures.net.StudentsListActivity
import hr.fer.tel.ruazosa.lectures.entity.Course
import hr.fer.tel.ruazosa.lectures.entity.ShortCourse
import hr.fer.tel.ruazosa.lectures.entity.ShortPerson
import hr.fer.tel.ruazosa.lectures.net.RestFactory
import kotlinx.android.synthetic.main.activity_course_details.*
import kotlinx.android.synthetic.main.add_student_pop_up.view.*

class CourseDetailsActivity : AppCompatActivity() {

    private var course: Course? = null
    private var courseName: TextView? = null
    private var courseDescription: TextView? = null
    private var teacher: TextView? = null
    private var courseStudentsButton: Button? = null
    private var newStudents: MutableList<ShortPerson>? = mutableListOf()
    private var shCourse: ShortCourse? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_details)

        courseName = findViewById(R.id.courseName) as TextView
        courseDescription = findViewById(R.id.courseDescription) as TextView
        teacher = findViewById(R.id.courseTeacher) as TextView
        courseStudentsButton = findViewById(R.id.courseStudentsButton) as Button

        val shortCourse = intent.getSerializableExtra("course") as ShortCourse
        shCourse = shortCourse
        LoadStudentsTask().execute(shortCourse?.id)

        LoadShortCourseTask().execute(shortCourse)

        courseStudentsButton!!.setOnClickListener {
            val intent = Intent(this, StudentsListActivity::class.java)
            intent.putExtra("id", course?.id)
            //Log.d("HEY", course?.id.toString())
            startActivity(intent)
        }

        addStudent.setOnClickListener {
            val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
            val view = LayoutInflater.from(this).inflate(R.layout.add_student_pop_up, null)
            dialogBuilder.setCancelable(true)
            dialogBuilder.setView(view)
            val alertDialog = dialogBuilder.create()
            if(newStudents != null){
                val adapter = StudentsAdapter(this,
                    android.R.layout.simple_list_item_1, newStudents!!)
                view.newStudentsList.adapter = adapter
            }
            view.newStudentsList.onItemClickListener = AdapterView.OnItemClickListener{parent, _, position, _ ->
                val itemAtPosition = parent.getItemAtPosition(position)
                val shp = itemAtPosition as ShortPerson
                EnrollStudent().execute(shp.id)
                alertDialog.dismiss()
                Toast.makeText(this, "Person added to the course", Toast.LENGTH_SHORT).show()
                val loadStudentsTask = LoadStudentsTask()
                loadStudentsTask.execute(shCourse?.id)
            }
            if (newStudents?.isEmpty()!!){
                Toast.makeText(this, "All students are enrolled on this class", Toast.LENGTH_SHORT).show()
            }else{
                alertDialog.show()
            }


        }
    }

    private inner class LoadShortCourseTask: AsyncTask<ShortCourse, Void, Course?>() {

        override fun doInBackground(vararg sCourse: ShortCourse): Course? {
            val rest = RestFactory.instance
            return rest.getCourse(sCourse[0].id)
        }

        override fun onPostExecute(newCourse: Course?) {
            course = newCourse
            courseName?.text = course?.name
            courseDescription?.text = course?.description

            this@CourseDetailsActivity.teacher?.text = course?.teacher?.name
        }
    }

    private inner class LoadStudentsTask: AsyncTask<Long?, Void, List<ShortPerson>>(){
        override fun onPostExecute(result: List<ShortPerson>?) {
            cardViewList(result)
        }

        override fun doInBackground(vararg p0: Long?): List<ShortPerson>? {
            val rest = RestFactory.instance
            var listOfNewStudents: MutableList<ShortPerson>? = mutableListOf()
            val persons = rest.getListOfPersons()
            var courseStudents = rest.getCourseStudents(p0[0])
            listOfNewStudents?.clear()
            persons?.forEach {
                if (it != null && courseStudents!=null) {
                    if (!(it in courseStudents)){
                        listOfNewStudents?.add(it)
                    }
                }
            }
            return listOfNewStudents
        }
    }

    private fun cardViewList(students: List<ShortPerson>?){
        newStudents?.clear()
        newStudents = students as MutableList<ShortPerson>?
    }

    private inner class StudentsAdapter(context: Context, textViewResourceId: Int, private val
    shortStudentsList: List<ShortPerson>) : ArrayAdapter<ShortPerson>(context, textViewResourceId, shortStudentsList)

    private inner class EnrollStudent: AsyncTask<Long?, Void, Unit>(){
        override fun doInBackground(vararg p0: Long?): Unit {
            val rest = RestFactory.instance
            if(p0[0] != null){
                Log.d("COURSE_ID", shCourse?.id.toString())
                Log.d("PERSON_ID", p0[0].toString())
                rest.enrollPersonToCourse(shCourse?.id, p0[0])
            }

        }
    }

    override fun onResume() {
        val loadStudentsTask = LoadStudentsTask()
        loadStudentsTask.execute(shCourse?.id)
        return super.onResume()
    }
}
