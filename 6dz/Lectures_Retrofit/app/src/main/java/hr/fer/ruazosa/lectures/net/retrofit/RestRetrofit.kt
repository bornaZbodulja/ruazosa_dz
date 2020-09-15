package hr.fer.tel.ruazosa.lectures.net.retrofit

import hr.fer.tel.ruazosa.lectures.entity.Course
import hr.fer.tel.ruazosa.lectures.entity.Person
import hr.fer.tel.ruazosa.lectures.entity.ShortCourse
import hr.fer.tel.ruazosa.lectures.entity.ShortPerson
import hr.fer.tel.ruazosa.lectures.net.RestFactory
import hr.fer.tel.ruazosa.lectures.net.RestInterface
import retrofit.RestAdapter

class RestRetrofit : RestInterface {
    private val service: LecturesService

    init {
        val baseURL = "http://" + RestFactory.BASE_IP + ":8080/api/"
        val retrofit = RestAdapter.Builder()
                .setEndpoint(baseURL)
                .build()

        service = retrofit.create(LecturesService::class.java)
    }

    override fun getListOfCourses(): List<ShortCourse>? {
        return service.listOfCourses
    }

    override fun getCourse(id: Long?): Course? {
        return service.getCourse(id)
    }

    override fun getCourseStudents(courseId: Long?): List<ShortPerson>? {
        return service.getCourseStudents(courseId)
    }

    override fun getListOfPersons(): List<ShortPerson>? {
        return service.getPersons()
        //throw UnsupportedOperationException()
    }

    override fun getPerson(id: Long?): Person? {
        throw UnsupportedOperationException()
    }

    override fun enrollPersonToCourse(courseId: Long?, personId: Long?): Boolean? {
        //throw UnsupportedOperationException()
        return service.enrollPersonToCourse(courseId, personId)

    }

    override fun disenrollPersonFromCourse(courseId: Long?, personId: Long?): Boolean? {
        //throw UnsupportedOperationException()
        return service.disenrollPersonFromCourse(courseId, personId)
    }

    override fun deletePerson(personId: Long?): Boolean?{
        return service.deletePerson(personId)
    }
}
