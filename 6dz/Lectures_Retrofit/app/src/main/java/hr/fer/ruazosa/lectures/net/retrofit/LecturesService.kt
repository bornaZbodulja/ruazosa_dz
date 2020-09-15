package hr.fer.tel.ruazosa.lectures.net.retrofit


import hr.fer.tel.ruazosa.lectures.entity.Course
import hr.fer.tel.ruazosa.lectures.entity.ShortCourse
import hr.fer.tel.ruazosa.lectures.entity.ShortPerson
import retrofit.http.DELETE
import retrofit.http.GET
import retrofit.http.POST
import retrofit.http.Path

interface LecturesService {
    @get:GET("/courses")
    val listOfCourses: List<ShortCourse>

    @GET("/courses/{id}")
    fun getCourse(@Path("id") id: Long?): Course

    @GET("/courses/{id}/students")
    fun getCourseStudents(@Path("id") courseId: Long?): List<ShortPerson>

    @GET("/persons")
    fun getPersons(): List<ShortPerson>

    @POST("/courses/{course_id}/enrollPerson/{person_id}")
    fun enrollPersonToCourse(@Path("course_id") courseId: Long?, @Path("person_id") personId: Long?): Boolean?

    @POST("/courses/{course_id}/unenrollPerson/{person_id}")
    fun disenrollPersonFromCourse(@Path("course_id") courseId: Long?, @Path("person_id") personId: Long?): Boolean?

    @DELETE("/persons/{person_id}")
    fun deletePerson(@Path("person_id") personId: Long?): Boolean?
}
