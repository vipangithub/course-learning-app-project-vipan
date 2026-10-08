package com.example.courselearningapp.data

import android.content.Context
import androidx.room.Room
import com.example.courselearningapp.data.local.CourseDatabase
import com.example.courselearningapp.data.remote.MockCourseApi
import com.example.courselearningapp.data.repository.CourseRepository

object AppContainer {

    private lateinit var database: CourseDatabase

    private lateinit var courseApi: MockCourseApi
    lateinit var courseRepository: CourseRepository
        private set

    fun initialize(context: Context) {

        database = Room.databaseBuilder(
            context,
            CourseDatabase::class.java,
            "course_database"
        ).build()
        courseApi = MockCourseApi(context)
        courseRepository = CourseRepository(
            api = courseApi,
            dao = database.courseDao()
        )
    }
}