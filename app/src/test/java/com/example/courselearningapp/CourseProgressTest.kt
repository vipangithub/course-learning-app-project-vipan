package com.example.courselearningapp

import com.example.courselearningapp.data.model.Course
import com.example.courselearningapp.data.model.Lesson
import com.example.courselearningapp.data.model.progress
import org.junit.Assert.assertEquals
import org.junit.Test

class CourseProgressTest {

    @Test
    fun `course progress is calculated from completed lessons`() {

        val course = Course(
            id = 1,
            title = "Kotlin",
            instructor = "John",
            lessons = listOf(
                Lesson(1, "Introduction", true),
                Lesson(2, "Variables", true),
                Lesson(3, "Functions", false),
                Lesson(4, "Classes", false)
            )
        )

        val progress = course.progress()

        assertEquals(50, progress)
    }
}