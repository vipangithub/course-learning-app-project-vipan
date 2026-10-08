package com.example.courselearningapp.data.repository

import com.example.courselearningapp.data.local.dao.CourseDao
import com.example.courselearningapp.data.model.Course
import com.example.courselearningapp.data.model.CourseResult
import com.example.courselearningapp.data.model.Lesson
import com.example.courselearningapp.data.remote.MockCourseApi

class CourseRepository(
    private val api: MockCourseApi,
    private val dao: CourseDao
) {

    suspend fun getCourses(): CourseResult {
        return try {
            val courses = api.getCourses()
            saveCoursesToDatabase(courses)

            CourseResult(
                courses = courses,
                isOffline = false
            )
        } catch (e: Exception) {
            CourseResult(
                courses = getCoursesFromDatabase(),
                isOffline = true
            )
        }
    }

    suspend fun markLessonCompleted(lessonId: Int) {
        dao.markLessonCompleted(lessonId)
    }

    private suspend fun saveCoursesToDatabase(
        courses: List<Course>
    ) {
        val courseEntities = courses.map { course ->
            com.example.courselearningapp.data.local.entity.CourseEntity(
                id = course.id,
                title = course.title,
                instructor = course.instructor
            )
        }

        val lessonEntities = courses.flatMap { course ->
            course.lessons.map { lesson ->
                com.example.courselearningapp.data.local.entity.LessonEntity(
                    id = lesson.id,
                    courseId = course.id,
                    title = lesson.title,
                    completed = lesson.completed
                )
            }
        }

        dao.insertCourses(courseEntities)
        dao.insertLessons(lessonEntities)
    }

    private suspend fun getCoursesFromDatabase(): List<Course> {

        val courses = dao.getCourses()
        val lessons = dao.getLessons()

        return courses.map { courseEntity ->

            Course(
                id = courseEntity.id,
                title = courseEntity.title,
                instructor = courseEntity.instructor,
                lessons = lessons
                    .filter { it.courseId == courseEntity.id }
                    .map { lessonEntity ->
                        Lesson(
                            id = lessonEntity.id,
                            title = lessonEntity.title,
                            completed = lessonEntity.completed
                        )
                    }
            )
        }
    }
}