package hr.fer.ruazosa.lectures.net

import android.content.Context
import android.os.AsyncTask
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import hr.fer.ruazosa.lectures.R
import hr.fer.tel.ruazosa.lectures.entity.ShortPerson
import hr.fer.tel.ruazosa.lectures.net.RestFactory
import kotlinx.android.synthetic.main.student_item.view.*

class StudentsAdapter(students: List<ShortPerson>?, course: Long?, context: Context): RecyclerView.Adapter<StudentsAdapter.ViewHolder>() {

    var studentsList: List<ShortPerson>? = students
    val currentCourse: Long? = course
    val context = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.student_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
       if(studentsList != null){return studentsList?.size!!}
        else{return 0}
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val person = studentsList?.get(position)
        holder.itemView.studentName.text = person.toString()
        holder.itemView.deleteButton.setOnClickListener {
            val dissenrollStudent = DissenrollStudent()
            dissenrollStudent.execute(person?.id)
            Toast.makeText(context, "Person disenrolled from the course", Toast.LENGTH_SHORT).show()
        }
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

    }

    private inner class DissenrollStudent(): AsyncTask<Long?, Unit, Unit>(){
        override fun doInBackground(vararg p0: Long?) {
            val rest = RestFactory.instance
            rest.disenrollPersonFromCourse(currentCourse, p0[0])
            studentsList = rest.getCourseStudents(currentCourse)
        }

        override fun onPostExecute(result: Unit?) {
            notifyDataSetChanged()
        }
    }
}